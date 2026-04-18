<?php

namespace App\Http\Controllers;

use App\Models\Formation;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Tymon\JWTAuth\Facades\JWTAuth;
use App\Services\ActivityLogService;

class FormationController extends Controller
{
    public function index(Request $request)
    {
        $query = Formation::with('formateur:id,nom,prenom');

        if ($request->has('search')) {
            $query->where('titre', 'like', '%' . $request->search . '%');
        }

        if ($request->has('categorie')) {
            $query->where('categorie', $request->categorie);
        }

        if ($request->has('niveau')) {
            $query->where('niveau', $request->niveau);
        }

        $formations = $query->withCount('enrollments')->get();

        return response()->json($formations);
    }

    public function show($id)
    {
        $formation = Formation::with([
            'formateur:id,nom,prenom',
            'modules' => fn($q) => $q->orderBy('ordre')
        ])->withCount('enrollments')->find($id);

        if (!$formation) {
            return response()->json(['message' => 'Formation introuvable'], 404);
        }

        $formation->increment('nombre_vues');

        (new ActivityLogService())->log('course_view', [
            'course_id' => (int) $id,
            'user_id'   => optional(JWTAuth::user())->id,
        ]);

        return response()->json($formation);
    }
 
    public function destroy($id)
    {
        $formation = Formation::find($id);

        if (!$formation) {
            return response()->json(['message' => 'Formation introuvable'], 404);
        }

        $user = JWTAuth::user();

        if ($formation->formateur_id !== $user->id) {
            return response()->json([
                'message' => 'Vous ne pouvez supprimer que vos propres formations'
            ], 403);
        }

        (new ActivityLogService())->log('course_deleted', [
            'course_id'  => (int) $id,
            'deleted_by' => $user->id,
            'titre'      => $formation->titre,
        ]);
        
        $formation->delete();

        return response()->json(['message' => 'Formation supprimée']);
    }

    public function mesFormations()
    {
        $user = JWTAuth::user();

        $formations = Formation::where('formateur_id', $user->id)
            ->withCount(['enrollments', 'modules'])
            ->get();

        return response()->json($formations);
    }
}
