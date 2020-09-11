package ar.com.ojedavh.sendmeal.model;

public class Usuario {
    private int id;
    private String nombre;
    private String clave;
    private String email;
    private double credito;
    private Tarjeta tarjeta;
    private CuentaBancaria cuentaBancaria;

    public Usuario(int id, String nombre, String clave, String email, double credito, Tarjeta tarjeta, CuentaBancaria cuentaBancaria) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
        this.email = email;
        this.credito = credito;
        this.tarjeta = tarjeta;
        this.cuentaBancaria = cuentaBancaria;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public String getEmail() {
        return email;
    }

    public double getCredito() {
        return credito;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public CuentaBancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }
}
