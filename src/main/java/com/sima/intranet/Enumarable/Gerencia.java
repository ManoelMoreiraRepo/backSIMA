package com.sima.intranet.Enumarable;

import com.sima.intranet.Interface.Filtrable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public enum Gerencia implements Filtrable {

    GER01( "G01" , "GRUPO SIMA" , "GROUP"  ),
    GER02( "G02" , "AEROPUERTOS" , "GPS" ),//GPS
    GER03( "G03" , "GLOBAL" , "GLOBAL" ),
    GER04( "G04" , "EDENOR" , "GLOBAL" ), // GLOBAL
    GER05( "G05" , "LA BIZANTINA" , "LA BIZANTINA" ),
    GER06( "G06" , "ECOKLIN" , "ECOKLIN" );

    public String descrip;
    public String codigo;

    public String codigoOfertasEmpleo;

    Gerencia(String codigo , String descrip , String codigoOfertasEmpleo){
        this.codigo = codigo;
        this.descrip = descrip;
        this.codigoOfertasEmpleo = codigoOfertasEmpleo;
    }

    public static Gerencia getGerencia(Cell cell){
        if(cell==null){
            return null;
        }
        cell.setCellType(CellType.STRING);
        String dato = cell.getStringCellValue();
        for(Gerencia g : Gerencia.values()){
            if(g.codigo.equalsIgnoreCase(dato.trim())){
                return g;
            }
        }
        return null;
    }

    public static Gerencia getGerenciaParaImpOfertaEmpleo(String dato) {
        if(dato == null || dato.isBlank()){
            return null;
        }
        for(Gerencia g : Gerencia.values()){
            if(g.codigoOfertasEmpleo.equalsIgnoreCase(dato.trim())){
                return g;
            }
        }
        return null;

    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getId() {
        return name();
    }

    @Override
    public String getDescipcion() {
        return descrip;
    }

}
