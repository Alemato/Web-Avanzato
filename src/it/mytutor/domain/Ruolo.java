package it.mytutor.domain;

public class Ruolo {
    public static final Ruolo ADMIN = new Ruolo("ADMIN");
    public static final Ruolo STUDENT = new Ruolo("USER");
    public static final Ruolo TEACHER = new Ruolo("TEACHER");

    private String nome;

    private Ruolo(String nome){
        this.nome=nome;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

}
