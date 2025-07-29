package com.jsr_dev.medical_api.domain.appointment;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public enum CancellationReason {
    PATIENT_DESISTED("Patient desisted", "Paciente desistió"),
    PHYSICIAN_CANCELLATION("Physician desisted", "Médico canceló"),
    OTHER("Other", "Otro");

    private static final Map<String, CancellationReason> ENG_REASON_MAP = new HashMap<>();
    private static final Map<String, CancellationReason> ESP_REASON_MAP = new HashMap<>();

    static {
        for (CancellationReason reason : values()) {
            ENG_REASON_MAP.put(normalize(reason.engCancellationReason), reason);
            ESP_REASON_MAP.put(normalize(reason.espCancellationReason), reason);
        }
    }

    private final String engCancellationReason;
    private final String espCancellationReason;

    CancellationReason(String engCancellationReason, String espCancellationReason) {
        this.engCancellationReason = engCancellationReason;
        this.espCancellationReason = espCancellationReason;
    }

    public static CancellationReason fromEng(String text) {
        return ENG_REASON_MAP.get(normalize(text));
    }

    public static CancellationReason fromEsp(String text) {
        return ESP_REASON_MAP.get(normalize(text));
    }

    public static CancellationReason parseCancellationReason(String cancellationReason) {
        CancellationReason found = fromEsp(cancellationReason);
        if (found != null) return found;

        found = fromEng(cancellationReason);
        if (found != null) return found;

        throw new IllegalArgumentException("Cancellation Reason: " + cancellationReason + " not found in enum class");
    }

    public static String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
}