package com.sima.intranet.Service;

import com.sima.intranet.Entity.*;
import com.sima.intranet.Enumarable.*;
import com.sima.intranet.Interface.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ImportacionServiceImpl implements ImportacionInterface {
    private Logger logger = LoggerFactory.getLogger(ImportacionServiceImpl.class);

    @Autowired
    private EmpleadoInterface empledoService;
    @Autowired
    private CredencialInterface credencialService;

    @Autowired
    private OfertaInterface ofertaService;

    @Autowired
    private DiaInterface diaService;

    @Autowired
    private MovilInterface movilService;

    @Autowired
    private InfraccionInterface infraccionService;

    public static final List<String> FORMATO_BEJERMAN_NOMINA = List.of(
            "gerencia","txtLegNum", "txtApeNom", "per_celular", "per_dom", "per_piso", "per_dpto", "per_torre", "per_sector",
            "per_escalera", "per_telef", "per_CP", "per_prov", "per_entrecalles", "per_mail", "per_loc", "per_nest",
            "per_codarea", "per_ttel", "per_tmail", "per_locms", "per_sexo", "per_fNac", "per_estc", "per_tDoc", "per_ndoc",
            "per_nac", "per_cuil", "lab_cat", "lab_sec", "lab_pto", "lab_car", "lab_cla", "lab_basico", "lab_Ese",
            "lab_fingT", "lab_FIng", "lab_FIngR", "lab_FEgrT", "lab_fEgr", "lab_TNov", "lab_Meg", "lab_SitR", "lab_cco",
            "lab_domexp", "lab_puesto", "lab_FTelRenun", "lab_ale", "lab_sitms", "lab_ltra", "lab_ncons", "lab_fdes", "lab_lpg",
            "liq_FPag", "liq_Bco", "liq_tcta", "liq_cta", "liq_Situacion", "liq_Condicion", "liq_Actividad", "liq_Mpr",
            "liq_FVenc", "liq_FDes", "liq_fHas", "liq_OSo", "liq_porad", "liq_sin", "liq_afjp", "liq_tme", "liq_sinies", "liq_obs"
    );


    public static final List<String> FORMATO_SUELDO_NOMINA = List.of(
            "Legajo", "Apellido", "Nombre/s", "C.U.I.L.", "Sucursal", "Sección", "Objetivo",
            "Zona de pago", "Supervisor", "Ingreso", "Baja"
    );

    public static final List<String> FORMATO_LEGAJO_NOMINA = List.of(
            "Legajo", "Apellido", "Nombre/s", "Calle", "Número", "Piso", "Departamento",
            "Entre calle","y calle", "Barrio", "Localidad", "Partido", "Provincia", "Código postal",
            "Teléfono", "Celular", "eMail", "Fecha de nacimiento", "Lugar de nacimiento", "Edad",
            "Sexo", "Estado civil", "Nacionalidad", "Documento de identidad", "Fecha de ingreso",
            "Ingreso anterior 1", "Sueldo básico", "Categoría", "Sucursal", "Sección", "Objetivo",
            "Zona de pago", "C.U.I.L.", "Modalidad de contratación", "Situación", "Condición",
            "Actividad", "Zona geográfica", "CCT", "Seguro de vida", "Obra social", "DNI",
            "CODE GERENCIA", "GERENCIA", "CATEGORIA", "SINDICATO"
    );

    public static final List<String> FORMATO_CREDENCIALES = List.of(
            "Apellido", "Nombre/s", "C.U.I.L.", "DNI", "CREDENCIAL FISICA", "NOTA", "VENCIMIENTO", "JURISDICCION", "GERENCIA"
    );


    public static final List<String> FORMATO_OFERTAS_EMPLEO = List.of("EMPRESA","CODIGO","ZONA","TITULO" ,"BREVE DESCRIPCION" ,"REQUISITOS" , "SE OFRECE");


    public static final List<String> FORMATO_GRILLA = List.of("GRILLA");

    public static final List<String> FORMATO_INSERSION_MOVILES = List.of("Nro" , "DOMINIO" , "ESTADO" , "ASIGNADO A:" , "DESTINO" , "COD.GERENCIA");

    public static final List<String> FORMATO_INFRACCIONES = List.of("Marca temporal", "ACTA Nro.", "SECCIÓN", "Dirección de correo electrónico", "FECHA del Acta",
            "PATENTE Nro", "GERENCIA Operativa", "Importe", "Motivo", "Link de la Multa", "ENVIAR A:", "ASIGNADA A:", "FECHA", "DNI",
            "FORMA DE PAGO", "COMPROBANTE DE PAGO", "CIERRE DE ACTA", "NOTAS");


    /**
     * Metodo asincrono que determina cual formato se esta intentando actualizar.
     * @param ruta
     */
    @Async
    public void procesarImportacion(String ruta) {
        try {
            FileInputStream excelFile = new FileInputStream(ruta);
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet sheet = workbook.getSheetAt(0);

            List<String> cabezera = new ArrayList<>();

            for (Cell cell : sheet.getRow(0)) {
                cabezera.add(getStringValorCelda(cell));
            }
            System.out.println(cabezera);


            if (FORMATO_BEJERMAN_NOMINA.containsAll(cabezera)) {
                logger.info("Actualiacion de FORMATO BEGERMAN iniciada.");
                insertarFormatoBejerman(sheet);
            } else if (cabezera.containsAll(FORMATO_SUELDO_NOMINA)) {
                logger.info("Actualiacion de SUELDO.");
                insertarFormatoSueldo(sheet);
            } else if(FORMATO_LEGAJO_NOMINA.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO LEGAJO");
                insertarFormatoLegajo(sheet);
            } else if(FORMATO_CREDENCIALES.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO CREDENCIALES");
                insertarFomatoCredenciales(sheet);
            }else if(FORMATO_OFERTAS_EMPLEO.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO OFERTAS EMPLEO.");
                insertarFomatoOfertasEmpleo(sheet);
            } else if(cabezera.containsAll(FORMATO_GRILLA)){
                logger.info("Actualiacion de FORMATO OFERTAS GRILLA.");
                insertarFormatoGrilla(sheet);
            } else if(FORMATO_INSERSION_MOVILES.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO INSERSION_MOVILES.");
                insertarFomatoMoviles(sheet);
            }else if(FORMATO_INFRACCIONES.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO INFRACCIONES.");
                insertarFormatoInfracciones(sheet);
            }else{
                cabezera.removeIf((dato) -> FORMATO_OFERTAS_EMPLEO.contains(dato));
                logger.info("El formato de este excel no esta implementado.");
                System.out.println("Cabezeras no reconocidas");
                System.out.println(cabezera);
            }
            workbook.close();
            excelFile.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("Error al intentar realizar la importacion.");
        }

    }

    /**
     * Realiza el cruce de informacion entre Movil y infracciones.
     * @param sheet
     */
    private void insertarFormatoInfracciones(Sheet sheet) {
        List<Infraccion> infraccionList = new ArrayList<>();
        for(Row row :sheet){
            if(row.getRowNum() == 0){
                continue;
            }
            String dominio = getStringValorCelda(row.getCell(5));
            if(dominio==null){
                logger.error("Dominio no encontrado. Row NRO : " + row.getRowNum());
                continue;
            }

            Optional<Movil> movil = movilService.getByDominio(dominio);

            if(!movil.isPresent()){
                logger.error("No se encontro el movil para poder cruzar la informacion. Dominio: " + dominio);
                continue;
            }

            String numeroActa = getStringValorCelda(row.getCell(1));

            if(numeroActa == null){
                logger.error("No se encontro el numero de acta. Row numero : " + row.getRowNum());
                continue;
            }

            Infraccion infraccion = infraccionService.buscarPorNumeroActa(numeroActa).orElse(new Infraccion());

            infraccion.setNumero(numeroActa);

            Cell cellFechaActua = row.getCell(4);
            try {
                if(cellFechaActua.getDateCellValue()!=null){
                    if(!cellFechaActua.getCellType().equals(CellType.NUMERIC)){
                        infraccion.setFechaActa(getLocalDateFromExcel(cellFechaActua.getDateCellValue()));
                    }else{
                        infraccion.setFechaActa(getLocalDateFromExcelNumeric(cellFechaActua.getNumericCellValue()));
                    }
                }

            }catch (IllegalStateException e){
                logger.error("Fecha de acta invalida, Acta NRO :  " + numeroActa);
            }

            infraccion.setMovil(movil.get());
            infraccion.setImporte(getBigDecimalFromExcel(row.getCell(7)));
            infraccion.setMotivo(getStringValorCelda(row.getCell(8)));
            infraccion.setAsignado(getStringValorCelda(row.getCell(11)));
            infraccion.setFormaPago(getStringValorCelda(row.getCell(14)));
            infraccion.setEstado(EstadoInfraccion.getEstadoParaImportacion(getStringValorCelda(row.getCell(16))));

            infraccionList.add(infraccion);
        }
        infraccionService.saveAll(infraccionList);
    }


    /**
     * Ingresa la informacion de los moviles existentes.
     * @param sheet
     */
    private void insertarFomatoMoviles(Sheet sheet) {
        List<Movil> moviles = new ArrayList<>();
        for(Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            String dominio = getStringValorCelda(row.getCell(1));

            if(dominio  == null){
                logger.error("Dominio no encontrado.");
                continue;
            }

            Movil movil = movilService.getByDominio(dominio).orElse(new Movil());

            movil.setNumero(getStringValorCelda(row.getCell(0)));
            movil.setDominio(dominio);
            movil.setEstado(EstadoMovil.getEstadoMovilImportacion(getStringValorCelda(row.getCell(2))));
            movil.setAsignado(getStringValorCelda(row.getCell(3)));
            movil.setDestino(getStringValorCelda(row.getCell(4)));
            movil.setGerencia(Gerencia.getGerencia(getStringValorCelda(row.getCell(5))));

            moviles.add(movil);

        }

        movilService.saveAll(moviles);

    }

    /**
     * Ingresa la grilla de un empleado.
     * @param sheet
     */
    private void insertarFormatoGrilla(Sheet sheet) {
        Cell primeraFecha = sheet.getRow(0).getCell(1);
        if(primeraFecha == null){
            logger.error("Primera fecha no encontrada.");
            return;
        }
        primeraFecha.setCellType(CellType.STRING);
        LocalDate fechaInicio = getLocalDateFromExcelNumeric(Double.valueOf(primeraFecha.getStringCellValue()));

        for(Row row : sheet){
            if(row.getRowNum()<3){
                continue;
            }
            String dni = getStringValorCelda(row.getCell(0));
            if(dni == null || dni.isEmpty()){
                logger.error("DNI no ENCONTRADO. row:"+ row.getRowNum());
                continue;
            }
            Optional<Empleado> empleadoOpt = empledoService.findByDNI(dni);

            if(!empleadoOpt.isPresent()){
                logger.error("Empleado no encontrado : " + dni);
                continue;
            }
            Integer cantidadDias = 0;
            List<Dia> diasIngresados = new ArrayList<>();
            for (Cell cell : row) {
                if(cell.getColumnIndex()>2 && cell.getColumnIndex()<33){
                    String estadoString = getStringValorCelda(cell);
                    EstadoDia estado =  EstadoDia.getEstadoDiaImportacion(estadoString);
                    if(estado!=null){
                        LocalDate fechaAplica = fechaInicio.plusDays(cantidadDias);
                        Dia dia = diaService.buscarPorFechaYEmpleado(fechaAplica , empleadoOpt.get()).orElse(new Dia());
                        dia.setEmpleado(empleadoOpt.get());
                        dia.setFecha(fechaAplica);
                        dia.setEstado(estado);
                        diasIngresados.add(dia);
                    }
                    if(estadoString != null){
                        logger.error("ESTADO NO RECONOCIDO : " + estadoString);
                    }
                    cantidadDias++;
                }
            }
            diaService.saveAll(diasIngresados);
        }

    }

    /**
     * Ingresa las ofertas de empleo.
     * @param sheet
     */
    private void insertarFomatoOfertasEmpleo(Sheet sheet) {
        List<Oferta> ofertas = new ArrayList<>();
        for(Row row : sheet){
            if (row.getRowNum() == 0) {
                continue;
            }
            String strinGerencia = getStringValorCelda(row.getCell(0));
            Gerencia gerencia = Gerencia.getGerenciaParaImpOfertaEmpleo(strinGerencia);
            String stringCodigo = getStringValorCelda(row.getCell(1));

            if(gerencia==null || stringCodigo == null){
                logger.error("Empresa o codigo no encontrado fila nro: " + row.getRowNum());
                continue;
            }

            Oferta oferta = ofertaService.getOferta(stringCodigo,gerencia).orElse(Oferta.builder()
                            .gerencia(gerencia)
                            .codigo(stringCodigo)
                            .build());
            oferta.setTitulo(getStringValorCelda(row.getCell(2)));
            oferta.setZona(getStringValorCelda(row.getCell(3)));
            oferta.setDescripcion(getStringValorCelda(row.getCell(4)));
            oferta.setRequisitos(getStringValorCelda(row.getCell(5)));
            oferta.setSeOfrece(getStringValorCelda(row.getCell(6)));
            ofertas.add(oferta);
        }
        ofertaService.saveAll(ofertas);
    }

    @Transactional
    private void insertarFomatoCredenciales(Sheet sheet) {
        for(Row row : sheet){
            if (row.getRowNum() == 0) {
                continue;
            }
            Cell celdaDni = row.getCell(3);
            celdaDni.setCellType(CellType.STRING);
            String dni = celdaDni.getStringCellValue().trim();
            if(dni.isEmpty()){
                continue;
            }
            Optional<Empleado> empleadoOpt = empledoService.findByDNI(dni);

            if(!empleadoOpt.isPresent()){
                logger.error("Empleado no encontrado, DNI : " + dni);
                continue;
            }
            Empleado empleado = empleadoOpt.get();
            Cell celGerencia = row.getCell(8);
            celGerencia.setCellType(CellType.STRING);
            Gerencia gerencia = Gerencia.getGerencia(celGerencia.getStringCellValue());

            Cell celJurisdiccion = row.getCell(7);
            celJurisdiccion.setCellType(CellType.STRING);
            Jurisdiccion jurisdiccion = Jurisdiccion.getJurisdiccion(celJurisdiccion.getStringCellValue());

            if(gerencia == null || jurisdiccion == null){
                logger.error("Jurisdiccion o generencia invalida, DNI : " + dni);
                continue;
            }
            Credencial credencial = credencialService.findByJurisdiccionAndGerencia(jurisdiccion , gerencia , empleado);

            if(credencial == null){
                credencial = new Credencial();
                credencial.setJurisdiccion(jurisdiccion);
                credencial.setEmpleado(empleado);
                credencial.setGerencia(gerencia);
            }
            Cell cellTipo = row.getCell(4);
            cellTipo.setCellType(CellType.STRING);
            credencial.setTipo(TipoCredencial.getTipoCredencialImportacion(cellTipo.getStringCellValue()));
            Cell cellVencimiento = row.getCell(6);
            try {
                if(cellVencimiento.getDateCellValue()!=null){
                    if(!cellVencimiento.getCellType().equals(CellType.NUMERIC)){
                        credencial.setFechaVencimentoCredencial(getLocalDateFromExcel(cellVencimiento.getDateCellValue()));
                    }else{
                        credencial.setFechaVencimentoCredencial(getLocalDateFromExcelNumeric(cellVencimiento.getNumericCellValue()));
                    }
                }

            }catch (IllegalStateException e){
                logger.error("Fecha de vencimiento invalida, DNI: " + dni);
            }
            if(empleado.getCredencial() == null) {
                empleado.setCredencial(new ArrayList<>());
            }
            empleado.getCredencial().add(credencial);
            credencialService.save(credencial);
        }
    }



    /**
     * Realiza la insercion de dato sueldo total para los empleado que ya encuentran en la base de datos.
     * @param sheet
     */
    private void insertarFormatoSueldo(Sheet sheet) {
        List<Empleado> lista = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() >= 0 && row.getRowNum() <= 3) {
                continue;
            }
            Double sueldoTotal = Double.valueOf(0);
            Cell celdaDni = row.getCell(3);
            String dni = celdaDni.getStringCellValue().substring(3, 11);
            if(dni.isEmpty()){
                continue;
            }
            Optional<Empleado> empleadoOpt = empledoService.findByDNI(dni);
            if(empleadoOpt.isPresent()){
                Empleado empleado = empleadoOpt.get();
                for (Cell cell : row) {
                    try {
                        if (cell.getColumnIndex() > 10) {
                            try {
                                sueldoTotal += cell.getNumericCellValue();
                            } catch (Exception e) {
                                logger.error("Dato invalido para sumar al sueldo total");
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex() );
                    }
                }
                if(empleado!=null){
                    empleado.setSueldoTotal(new BigDecimal(sueldoTotal));
                    lista.add(empleado);
                }
            }
        }
        empledoService.saveAll(lista);

    }

    /**
     * Inserta toda la informacion basica del empleado.
     * @param sheet
     */
    private void insertarFormatoLegajo(Sheet sheet) {
        List<Empleado> listaEmpleados = new ArrayList<>();
        Set<String> dnis = new HashSet<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Cell celdaDni = row.getCell(41);
            celdaDni.setCellType(CellType.STRING);
            String dni = celdaDni.getStringCellValue().trim();
            if(dni.isEmpty()){
                continue;
            }

            if(dnis.contains(dni.trim())){
                continue;
            }else{
                dnis.add(dni.trim());
            }
            Empleado empleado = empledoService.findByDNI(dni).orElse(new Empleado(dni));
            Gerencia gerencia = null;
            for (Cell cell : row) {

                try {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            empleado.setLegajoEmpleado(String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue()));
                            break;
                        case 1:
                            empleado.setApellidoEmpleado(cell.getStringCellValue());
                            break;
                        case 2:
                            empleado.setNombreEmpleado(cell.getStringCellValue());
                            break;
                        case 3:
                            cell.setCellType(CellType.STRING);
                            empleado.setDireccionEmpleado(cell.getStringCellValue());
                            break;
                        case 4:
                            cell.setCellType(CellType.STRING);
                            empleado.setDireccionEmpleado(empleado.getDireccionEmpleado() + " " + cell.getStringCellValue());
                            break;
                        case 13:
                            empleado.setCodigoPostalEmpleado(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case 15:
                            cell.setCellType(CellType.STRING);
                            empleado.setTelefonoEmpleado(String.valueOf(cell.getStringCellValue()));
                            break;
                        case 16:
                            empleado.setEmailEmpleado(cell.getStringCellValue());
                            break;
                        case 17:
                            empleado.setFechaNascimentoEmpleado(getLocalDateFromExcel(cell.getDateCellValue()));
                            break;
                        case 24:
                            empleado.setFechaAltaEmpleado(getLocalDateFromExcel(cell.getDateCellValue()));
                            break;
                        case 27:
                            empleado.setCargoEmpleado(cell.getStringCellValue());
                            break;
                        case 30:
                            empleado.setObjetivoEmpleado(cell.getStringCellValue());
                            break;
                        case 34:
                            empleado.setEstadoEmpleado(cell.getStringCellValue());
                            break;
                        case 42:
                            gerencia = Gerencia.getGerencia(cell.getStringCellValue());
                            if(gerencia!=null){
                                empleado.setGerencia(gerencia);
                            }
                            break;
                        case 45:
                            Sindicato s = null;
                            try {
                                s = Sindicato.getSindicatoImportacion(cell.getStringCellValue());
                            }catch (IllegalArgumentException e){
                                logger.error("Sindicato no reconocido : " + cell.getStringCellValue());
                            }
                            empleado.setSindicato(s);
                            break;

                        default:
                            //No me sirve el dato.
                    }
                } catch (Exception e) {
                    logger.error("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex() );
                }
            }
            if(empleado!=null){
                listaEmpleados.add(empleado);
            }
        }
        empledoService.saveAll(listaEmpleados);
    }

    private void insertarFormatoBejerman(Sheet sheet) {
        List<Empleado> lista = new ArrayList<>();
        Set<String> dnis = new HashSet<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Cell celdaDni = row.getCell(25);
            celdaDni.setCellType(CellType.STRING);
            String dni = celdaDni.getStringCellValue();
            if(dni.isEmpty()){
                continue;
            }

            if(dnis.contains(dni.trim())){
                continue;
            }else{
                dnis.add(dni.trim());
            }
            Empleado empleado = empledoService.findByDNI(dni).orElse(new Empleado(dni));
            Gerencia gerencia = null;
            for (Cell cell : row) {
                try {
                    switch (cell.getColumnIndex()) {
                        case 0:
                            gerencia = Gerencia.getGerencia(cell.getStringCellValue());
                            if(gerencia!=null){
                                empleado.setGerencia(gerencia);
                            }
                            break;
                        case 1:
                            empleado.setLegajoEmpleado(String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue()));
                            break;
                        case 2:
                            empleado.setNombreEmpleado(getNomnbreApellidoSplit(cell.getStringCellValue(), 1));
                            empleado.setApellidoEmpleado(getNomnbreApellidoSplit(cell.getStringCellValue(), 0));
                            break;
                        case 3:
                            cell.setCellType(CellType.STRING);
                            empleado.setTelefonoEmpleado(getStringSinEspacios(cell.getStringCellValue()));
                            break;
                        case 4:
                            empleado.setDireccionEmpleado(cell.getStringCellValue());
                            break;
                        case 11:
                            empleado.setCodigoPostalEmpleado(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case 22:
                            empleado.setFechaNascimentoEmpleado(getLocalDateFromExcel(cell.getDateCellValue()));
                            break;
                        case 25:
                            cell.setCellType(CellType.STRING);
                            empleado.setDNIEmpleado(cell.getStringCellValue());
                            break;
                        case 29:
                            empleado.setObjetivoEmpleado(cell.getStringCellValue());
                            break;
                        case 31:
                            empleado.setCargoEmpleado(cell.getStringCellValue());
                            break;
                        case 64:
                            Sindicato s = null;
                            try {
                                s = Sindicato.getSindicatoImportacion(cell.getStringCellValue());
                            }catch (IllegalArgumentException e){
                                logger.error("Sindicato no reconocido : " + cell.getStringCellValue());
                            }
                            empleado.setSindicato(s);
                            break;
                        default:
                            //No me sirve el dato.
                    }
                } catch (Exception e) {
                    logger.error("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex() );
                }
            }
            if(empleado!=null){
                lista.add(empleado);
            }

        }
        empledoService.saveAll(lista);
    }

    /**
     * Para obetener nombre o apellido, el index puede ser 0(Apellido) o 1 (Nombre);
     *
     * @param completo
     * @param index
     * @return
     */
    private String getNomnbreApellidoSplit(String completo, Integer index) { //RIOS, ALAN GONZALO
        String[] parts = completo.split(",");
        String resultado = "";
        if (parts.length >= 2 && (index.equals(0) || index.equals(1))) {
            resultado = parts[index].trim().replace(",", "");
        }
        return resultado;
    }

    /**
     * Retornar el string sin espacion en todo el string.
     *
     * @param data
     * @return
     */
    private String getStringSinEspacios(String data) {
        return data.replace(" ", "");
    }

    /**
     * Retorna el valor de la celda detendiento de tipo de celda.
     *
     * @param cell
     * @return
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return "";
        }
    }

    /**
     * Retorna el nombre de la celda del index indicado.
     *
     * @param sheet
     * @param columnIndex
     * @return
     */
    private static String getColumnName(Sheet sheet, int columnIndex) {
        Row headerRow = sheet.getRow(0);
        Cell headerCell = headerRow.getCell(columnIndex);
        return getCellValueAsString(headerCell);
    }

    private static String getUltimos8Digitos(String cadena){
        cadena = cadena.replaceAll("\\s", "");
        if (cadena.length() >= 8) {
            return cadena.substring(cadena.length() - 8);
        } else {
            return "";
        }
    }

    private static String getStringValorCelda(Cell cell){
        if(cell == null){
            return null;
        }
        cell.setCellType(CellType.STRING);
        if(cell.getStringCellValue().isEmpty()){
            return null;
        }
        return cell.getStringCellValue().trim();
    }

    private BigDecimal getBigDecimalFromExcel(Cell cell) {
        if(cell == null){
            return null;
        }
        if(cell.getCellType().equals(CellType.NUMERIC)){
            return BigDecimal.valueOf(cell.getNumericCellValue());
        }else{
            return BigDecimal.ZERO;
        }
    }

    private LocalDate getLocalDateFromExcel(Date dateCellValue) {

        if(dateCellValue == null){
            return null;
        }

        return dateCellValue.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

    }

    private LocalDate getLocalDateFromExcelNumeric(Double dateCellValue) {

        if(dateCellValue == null){
            return null;
        }
        return DateUtil.getJavaDate(dateCellValue).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
