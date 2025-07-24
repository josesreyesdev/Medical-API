package com.jsr_dev.medical_api.domain.appointment;

import java.text.Normalizer;
import java.util.Arrays;

public enum CancellationReason {
    PATIENT_DESISTED("Patient desisted", "Paciente desistió"),
    PHYSICIAN_CANCELLATION("Physician desisted", "Médico canceló"),
    OTHER("Other", "Otro");

    private final String engCancellationReason;
    private final String espCancellationReason;

    CancellationReason(String engCancellationReason, String espCancellationReason) {
        this.engCancellationReason = engCancellationReason;
        this.espCancellationReason = espCancellationReason;
    }

    public static CancellationReason fromEng(String text) {
        return Arrays.stream(values())
                .filter(g -> g.engCancellationReason.equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }

    public static CancellationReason fromEsp(String text) {
        String normalizedText = normalize(text);
        return Arrays.stream(values())
                .filter(g -> normalize(g.espCancellationReason).equalsIgnoreCase(normalizedText))
                .findFirst()
                .orElse(null);
    }

    public static CancellationReason parseCancellationReason(String cancellationReason) {
        CancellationReason found = fromEsp(cancellationReason);
        if (found != null) return found;

        found = fromEng(cancellationReason);
        if (found != null) return found;

        throw new IllegalArgumentException("Cancellation Reason: " + cancellationReason + " not found in enum class");
    }

    private static String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
}
