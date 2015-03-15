package test;

import javaMailEA.javaMailEA;
public class test {
    public static void main(String [] args){
        javaMailEA j = new javaMailEA();
        String resultado  = j.EnviarCorreo("gmail",
                "example@gmail.com", 
                "*****",
                "example@hotmail.com",
                "Prueba de correo",
                "<h1>Cabezera </h1><div style='background:blue;color:white;'>hola mundo</div>", 
                true);
        System.out.println(resultado);
    }
}
