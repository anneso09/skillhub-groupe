<?php

namespace Database\Factories;

use App\Models\User;
use Illuminate\Database\Eloquent\Factories\Factory;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;

/**
 * @extends Factory<User>
 */
class UserFactory extends Factory
{
    protected $model = User::class;

    public function definition(): array
    {
        return [
    'nom'      => 'Test',
    'prenom'   => 'User',
    'email'    => 'test' . rand(1,1000) . '@test.com',
    'password' => Hash::make('password123'),
    'role'     => 'apprenant',
];
    }

    public function formateur(): static
    {
        return $this->state(['role' => 'formateur']);
    }

    public function apprenant(): static
    {
        return $this->state(['role' => 'apprenant']);
    }
}
