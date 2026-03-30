<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;
use Tymon\JWTAuth\Contracts\JWTSubject;

class User extends Authenticatable implements JWTSubject
{
    protected $fillable = [
        'nom',
        'prenom',
        'email',
        'password',
        'role',
    ];

    protected $hidden = [
        'password',
    ];

    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    public function getJWTCustomClaims()
    {
        return [];
    }

    public function formations()
    {
        return $this->hasMany(Formation::class, 'formateur_id');
    }

    public function enrollments()
    {
        return $this->hasMany(Enrollment::class, 'utilisateur_id');
    }
}