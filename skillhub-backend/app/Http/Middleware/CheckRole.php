<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;

class CheckRole
{
    public function handle(Request $request, Closure $next, string $role)
    {
<<<<<<< Updated upstream
       $user = (object) [     'role' => $request->auth_user_role ];
=======
        $user = (object) [
            'role' => $request->auth_user_role
        ];
>>>>>>> Stashed changes

        if (!$user || $user->role !== $role) {
            return response()->json([
                'message' => 'Accès refusé. Rôle requis : ' . $role
            ], 403);
        }

        return $next($request);
    }
}