package com.sima.intranet.Enumarable;

public enum ConjuntoIndumentaria {
     AEP_001("AEP001");
     public String descripcion;
     ConjuntoIndumentaria(String decrip){
         this.descripcion = decrip;
     }

    public static ConjuntoIndumentaria getConjuntoImportacion ( String descrip){
        if(descrip == null){
            return null;
        }

        for (ConjuntoIndumentaria c : ConjuntoIndumentaria.values()){
            if(c.descripcion.equalsIgnoreCase(descrip.trim())){
                return c;
            }
        }
        return null;
    }
}
