package data.dashboard;

import java.time.LocalDateTime;

public class RegistroError {
    private String descripcion;
    private LocalDateTime timestamp;

    public RegistroError(String descripcion) {
        this.descripcion = descripcion;
        this.timestamp = LocalDateTime.now();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
