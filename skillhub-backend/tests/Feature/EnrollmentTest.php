<?php

namespace Tests\Feature;

use Tests\TestCase;

class EnrollmentTest extends TestCase
{
    public function test_limite_inscriptions()
    {
        $response = $this->postJson('/api/formations/1/inscription');

        $response->assertStatus(400);
    }
}