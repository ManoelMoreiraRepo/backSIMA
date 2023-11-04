package com.sima.intranet.Enumarable;

public enum Sindicato {
    UPADEP("U.P.A.D.E.P.", "UPADEP"),
    SUVICO("S.U.V.I.C.O.", "SUVICO"),
    SUTCA("S.U.T.C.A.", "SUTCA"),
    SUTCAPRA("S.U.T.C.A.P.R.A.", "SUTCAPRA"),
    SOM("S.O.M.", "SOM"),
    UPSRA("U.P.S.R.A.", "UPSRA"),
    OSCEARA("CAMARA EMPRESARIOS REMISES", "OSCEARA"),
    OSPACA("O.S  AUTOMOVIL CLUB ARGENTINO", "OSPACA"),
    OSDEM("O.S DE MUSICOS", "OSDEM"),
    OSPM("O.S PROGRAMAS MEDICOS SOC ARG", "OSPM"),
    OSV("O.S VAREADORES", "OSV"),
    OSA("O.S. AERONAVEGANTES", "OSA"),
    OSAMOC("O.S. ASOC. MUT. PADRE GROTE", "OSAMOC"),
    OSPILM("O.S. IND. LADRILLERA A MAQUINA", "OSPILM"),
    OSMMED("O.S. MANDOS MEDIOS TELECOMUNIC", "OSMMED"),
    OSMEDICA("O.S. MEDICOS CIUDAD BS.AS.", "OSMEDICA"),
    OSMISS("O.S. MIN. SECRET y SUBSECRET.", "OSMISS"),
    OSME("O.S. P. MIN.ECO.OBRAS PUBLICAS", "OSME"),
    UPCN("O.S. PERSONAL CIVIL NACION", "UPCN"),
    OSEMM("O.S. PERSONAL MARINA MERCANTE", "OSEMM"),
    OS_MOSAISTA("O.S. PERSONAL MOSAISTA", "OS MOSAISTA"),
    OSPJN("O.S.E.P.J.N.A.N. GOOD YEAR", "OSPJN"),
    OSPAT("O.S.P. ACTIVIDAD DEL TURF", "OSPAT"),
    ASOCIACION_MUTUAL_SANCOR("O.S.P.A. ASOC. MUTAL SANCOR", "MUTUAL SANCOR"),
    OSPATCA("O.S.PER ADM Y TEC CONS Y AFINE", "OSPATCA"),
    OSPACA_CERVECEROS("O.S.PERS. ACT. CERVECERA", "OSPACA CERVECEROS"),
    OSRJA("O.S.RELOJEROS Y JOYEROS", "OSRJA"),
    OSSIMRA("O.S.SUP.IND. METALMECANICA RA", "OSSIMRA"),
    OSPM_MAESTRANZA("Obra Social Per. Maestranza", "OSPM MAESTRANZA"),
    OSOCNA("OS DE COMISARIOS NAVALES", "OSOCNA"),
    OSPEDYB("OS DEL PER DE DRAGADO Y BALIZA", "OSPEDYB"),
    OSPICA("OS DEL PER DE LA IND CUERO Y A", "OSPICA"),
    ANDAR("OS VIAJANTES VENDEDORES R ARG", "ANDAR"),
    SIN_OBRA_SOCIAL("SIN OBRA SOCIAL", "SIN OBRA SOCIAL");


    public String descrip;

    public String titulo;
    Sindicato(String descip , String titulo){
        this.titulo = titulo;
        this.descrip = descip;
    }
    public static Sindicato getSindicatoImportacion(String sindicato){
        for(Sindicato s : Sindicato.values()){
            if(s.descrip.equalsIgnoreCase(sindicato.trim())){
                return s;
            }
        }
        return null;
    }

    public static Sindicato getSindicato(String sindicato){
        for(Sindicato s : Sindicato.values()){
            if(s.name().equalsIgnoreCase(sindicato)){
                return s;
            }
        }
        return null;
    }
}
