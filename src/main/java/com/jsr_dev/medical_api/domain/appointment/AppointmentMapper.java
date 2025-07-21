package com.jsr_dev.medical_api.domain.appointment;

public class AppointmentMapper {
    public static String toAppointment(AddAppointmentRequest addConsultation){
        return "";
    }

    public static AppointmentResponse toAppointmentResponse(String appointment){
        return new AppointmentResponse(null, null, null, null);
    }
}
