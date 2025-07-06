package com.jsr_dev.medical_api.physician;

import java.text.Normalizer;
import java.util.Arrays;

public enum Specialty {
    ORTHOPEDICS("Orthopedics", "Ortopedia"),
    CARDIOLOGY("Cardiology", "Cardiología"),
    GYNAECOLOGY("Gynaecology", "Ginecología"),
    PEDIATRICS("Pediatrics", "Pediatría"),
    DERMATOLOGY("Dermatology", "Dermatología");

    private final String specialtyEng;
    private final String specialtyEsp;

    Specialty(String specialtyEng, String specialtyEsp) {
        this.specialtyEng = specialtyEng;
        this.specialtyEsp = specialtyEsp;
    }

    public static Specialty fromEng(String text) {
        return Arrays.stream(values())
                .filter(g -> g.specialtyEng.equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }

    public static Specialty fromEsp(String text) {
        String normalizedText = normalize(text);
        return Arrays.stream(values())
                .filter(g -> normalize(g.specialtyEsp).equalsIgnoreCase(normalizedText))
                .findFirst()
                .orElse(null);
    }

    public static Specialty parseSpecialty(String specialty) {
        Specialty found = fromEsp(specialty);
        if (found != null) return found;

        found = fromEng(specialty);
        if (found != null) return found;

        throw new IllegalArgumentException("Specialty: " + specialty + " not found in enum class");
    }

    private static String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
}
