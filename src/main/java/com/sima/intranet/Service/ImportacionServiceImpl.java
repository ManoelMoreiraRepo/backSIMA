package com.sima.intranet.Service;

import com.sima.intranet.Entity.*;
import com.sima.intranet.Enumarable.*;
import com.sima.intranet.Enumarable.Empresa;
import com.sima.intranet.Interface.*;
import com.sima.intranet.Repository.LogImportacionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


    @Autowired
    private IndumentariaServiceImpl indumentariaService;

    @Autowired
    private LogImportacionRepository logImportacionRepository;

    @Autowired
    private CapacitacionInterface capacitacionService;

    public static final List<String> FORMATO_BEJERMAN_NOMINA = List.of(
            "gerencia","empresa","txtLegNum", "txtApeNom", "per_celular", "per_dom", "per_piso", "per_dpto", "per_torre", "per_sector",
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
            "CODE GERENCIA", "GERENCIA", "CATEGORIA", "SINDICATO" , "EMPRESA"
    );

    public static final List<String> FORMATO_CREDENCIALES = List.of(
            "Apellido", "Nombre/s", "C.U.I.L.", "DNI", "CREDENCIAL FISICA", "NOTA", "VENCIMIENTO", "JURISDICCION", "GERENCIA"
    );


    public static final List<String> FORMATO_OFERTAS_EMPLEO = List.of("EMPRESA","CODIGO","ZONA","TITULO" ,"BREVE DESCRIPCION" ,"REQUISITOS" , "SE OFRECE");


    public static final List<String> FORMATO_GRILLA = List.of("GRILLA");

    public static final List<String> FORMATO_INSERSION_MOVILES = List.of("Nro" , "DOMINIO" , "ESTADO" , "ASIGNADO A:" , "DESTINO" , "COD.GERENCIA","EMPLEADO");

    public static final List<String> FORMATO_INFRACCIONES = List.of(
            "AÑO", "MES", "Marca temporal", "ACTA Nro.", "SECCIÓN", "Dirección de correo electrónico",
            "FECHA del Acta", "PATENTE Nro", "GERENCIA Operativa", "CodGer", "Importe", "Motivo",
            "Link de la Multa", "FECHA", "ACTA Nro.", "ASIGNADA A:", "DNI", "FORMA DE PAGO",
            "COMPROBANTE DE PAGO", "ENVIAR A:", "CIERRE DE ACTA", "NOTAS"
    );


    public static final List<String> FORMATO_INDUMENTARIA = List.of(
            "Apellido y Nombre", "LEGAJO", "DNI", "Ndoc", "NOMINA ACTIVA",
            "PRODUCTO", "MODELO", "TALLE", "CANT.", "FECHA"
    );

    public static final List<String> FORMATO_BASE_GPS = List.of("Leg", "Apellido y Nombre", "DNI", "Estado", "Vto Cred", "Foto", "AA2000", "F PSA",
            "Est. Cred", "Serv.", "Turno", "Cargo", "Vto AV", "Vto RX", "P004", "P005",
            "P011", "BRIG", "AMAM", "Mail", "Tel Particular", "hoy", "5", "15",
            "Direccion", "CP", "Fecha de nac.");


    /**
     * Metodo asincrono que determina cual formato se esta intentando actualizar.
     *
     * @param ruta
     * @param nombreArchivo
     * @param usuario
     */
    @Async
    public void procesarImportacion(String ruta, String nombreArchivo, Usuario usuario) {
        LogImportacion logImportacion = new LogImportacion(LocalDateTime.now() , nombreArchivo , usuario);
        try {
            FileInputStream excelFile = new FileInputStream(ruta);
            Workbook workbook = new XSSFWorkbook(excelFile);

            Sheet sheet = workbook.getSheetAt(0);

            List<String> cabezera = new ArrayList<>();
            if(sheet.getRow(0) == null){
                throw new IOException("No se reconoce el formato , No se encontraron las cabezeras.");
            }
            for (Cell cell : sheet.getRow(0)) {
                cabezera.add(getStringValorCelda(cell));
            }
           // System.out.println(cabezera);


            if (FORMATO_BEJERMAN_NOMINA.containsAll(cabezera)) {
                logger.info("Actualiacion de FORMATO BEGERMAN iniciada.");
                insertarFormatoBejerman(sheet , logImportacion);
            } else if (cabezera.containsAll(FORMATO_SUELDO_NOMINA)) {
                logger.info("Actualiacion de SUELDO.");
                insertarFormatoSueldo(sheet , logImportacion);
            } else if(FORMATO_LEGAJO_NOMINA.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO LEGAJO");
                insertarFormatoLegajo(sheet , logImportacion);
            } else if(FORMATO_CREDENCIALES.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO CREDENCIALES");
                insertarFomatoCredenciales(sheet , logImportacion);
            }else if(FORMATO_OFERTAS_EMPLEO.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO OFERTAS EMPLEO.");
                insertarFomatoOfertasEmpleo(sheet , logImportacion);
            } else if(cabezera.containsAll(FORMATO_GRILLA)){
                logger.info("Actualiacion de FORMATO OFERTAS GRILLA.");
                insertarFormatoGrilla(sheet , logImportacion);
            } else if(FORMATO_INSERSION_MOVILES.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO INSERSION_MOVILES.");
                insertarFomatoMoviles(sheet , logImportacion);
            }else if(FORMATO_INFRACCIONES.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO INFRACCIONES.");
                insertarFormatoInfracciones(sheet , logImportacion);
            }else if(FORMATO_INDUMENTARIA.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO INFRACCIONES.");
                insertarFormatoIndumentaria(sheet , logImportacion);
            }else if(FORMATO_BASE_GPS.containsAll(cabezera)){
                logger.info("Actualiacion de FORMATO INFRACCIONES.");
                insertarFormatoBaseGPS(sheet , logImportacion);
            }else{
                //cabezera.removeIf((dato) -> FORMATO_OFERTAS_EMPLEO.contains(dato));
                //logger.info("El formato de este excel no esta implementado.");
                System.out.println("Cabezeras no reconocidas");
                System.out.println(cabezera);

            }
            workbook.close();
            excelFile.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("Error al intentar realizar la importacion.");
            logImportacion.addMensaje(e.getMessage());
        }finally {
            logImportacionRepository.save(logImportacion);
        }

    }

    private void insertarFormatoBaseGPS(Sheet sheet, LogImportacion logImportacion) {
        logImportacion.addMensaje("INICIO FORMATO BASE GPS.");
        List<Capacitacion> capacitacions = new ArrayList<>();
        for(Row row :sheet){
            if(row.getRowNum() == 0){
                continue;
            }

            String dni = getStringValorCelda(row.getCell(2));

            if(dni == null){
                logImportacion.addMensaje("Dni no encontrado. row nro : " + row.getRowNum());
                logger.error("Dni no encontrado. row nro : " + row.getRowNum());
                continue;
            }

            Optional<Empleado> empleado = empledoService.findByDNI(dni.trim());

            if(!empleado.isPresent()){
                logImportacion.addMensaje("Empleado no encontrado para realizar el cruce de informacion. DNI = " + dni );
                logger.error("Empleado no encontrado para realizar el cruce de informacion. DNI = " + dni);
                continue;
            }
            for(CursoHabilitante curso :CursoHabilitante.values()){
                LocalDate fecha = getLocalDateFromExcel(row.getCell(curso.columna) , logImportacion , row.getRowNum());
                if(fecha!=null){
                    Empleado empl = empleado.get();
                    Optional<Capacitacion> capacitacionOpt = capacitacionService.findByEmpleadoAndTipoCursoHabilitante(empl, curso);
                    Capacitacion cap = null;
                    if(capacitacionOpt.isPresent()){
                        cap = capacitacionOpt.get();
                        cap.setFechaVencimentoCapacitacion(fecha);
                    }else{
                        cap = new Capacitacion();
                        cap.setEmpleado(empl);
                        cap.setTipoCurso(curso);
                        cap.setFechaVencimentoCapacitacion(fecha);
                    }
                    capacitacions.add(cap);
                }
            }


        }
        capacitacionService.saveAll(capacitacions);
    }

    private LocalDate getLocalDateFromExcel(Cell cell, LogImportacion logImportacion, int rowNum) {
        LocalDate fecha = null;
        try {
            if(cell != null && cell.getDateCellValue()!=null){
                if(!cell.getCellType().equals(CellType.NUMERIC)){
                    fecha = getLocalDateFromExcel(cell.getDateCellValue());
                }else{
                    fecha = getLocalDateFromExcelNumeric(cell.getNumericCellValue());
                }
            }

        }catch (IllegalStateException e){
            logImportacion.addMensaje("Fecha invalida, Row nro :  " + rowNum);
            logger.error("Fecha invalida, Row nro :  " + rowNum);
        }
        return fecha;
    }

    private void insertarFormatoIndumentaria(Sheet sheet, LogImportacion logImportacion) {
        logImportacion.addMensaje("INICIO FORMATO INDUMENTARIA.");
        List<Indumentaria> indumentariaList = new ArrayList<>();
        for(Row row :sheet){
            if(row.getRowNum() == 0){
                continue;
            }

            String dni = getStringValorCelda(row.getCell(0));

            if(dni == null){
                logImportacion.addMensaje("Dni no encontrado. row nro : " + row.getRowNum());
                logger.error("Dni no encontrado. row nro : " + row.getRowNum());
                continue;
            }

            Optional<Empleado> empleado = empledoService.findByDNI(dni.trim());

            if(!empleado.isPresent()){
                logImportacion.addMensaje("Empleado no encontrado para realizar el cruce de informacion. DNI = " + dni );
                logger.error("Empleado no encontrado para realizar el cruce de informacion. DNI = " + dni);
                continue;
            }

            Cell cellFechaActua = row.getCell(6);
            LocalDate fechaEntrega = null;
            try {
                if(cellFechaActua.getDateCellValue()!=null){
                    if(!cellFechaActua.getCellType().equals(CellType.NUMERIC)){
                        fechaEntrega = getLocalDateFromExcel(cellFechaActua.getDateCellValue());
                    }else{
                        fechaEntrega = getLocalDateFromExcelNumeric(cellFechaActua.getNumericCellValue());
                    }
                }

            }catch (IllegalStateException e){
                logImportacion.addMensaje("Fecha de acta invalida, Row nro :  " + row.getRowNum());
                logger.error("Fecha de acta invalida, Row nro :  " + row.getRowNum());
            }

            if(fechaEntrega == null){
                logImportacion.addMensaje("fecha invalida " + row.getRowNum());
                logger.error("fecha invalida");
                continue;
            }
            Indumentaria indu = new Indumentaria();

            indu.setEmpleado(empleado.get());
            indu.setFechaUltimaEntregaIndumentaria(fechaEntrega);
            indu.setFechaProximaEntregaIndumentaria(fechaEntrega.plusYears(1));

            indu.setNombreIndumentaria(getStringValorCelda(row.getCell(2)));
            indu.setModeloIndumentaria(getStringValorCelda(row.getCell(3)));
            indu.setTalleIndumentaria(getStringValorCelda(row.getCell(4)));
            if(row.getCell(5)!=null){
                indu.setCantidad(Double.valueOf(row.getCell(5).getNumericCellValue()).longValue());
            }
            indu.setConjunto(ConjuntoIndumentaria.getConjuntoImportacion(getStringValorCelda(row.getCell(7))));
            indumentariaList.add(indu);
        }

        indumentariaService.saveAll(indumentariaList);

    }

    /**
     * Realiza el cruce de informacion entre Movil y infracciones.
     *
     * @param sheet
     * @param logImportacion
     */
    private void insertarFormatoInfracciones(Sheet sheet, LogImportacion logImportacion) {
        logImportacion.addMensaje("INICIO FORMATO INFRACCIONES.");
        List<Infraccion> infraccionList = new ArrayList<>();
        Set<String> numeros = new HashSet<>();
        for(Row row :sheet){
            if(row.getRowNum() == 0){
                continue;
            }
            String dominio = getStringValorCelda(row.getCell(7));
            if(dominio==null){
                logImportacion.addMensaje("Dominio no encontrado. Row NRO : " + row.getRowNum());
                logger.error("Dominio no encontrado. Row NRO : " + row.getRowNum());
                continue;
            }

            Optional<Movil> movil = movilService.getByDominio(dominio);

            if(!movil.isPresent()){
                logImportacion.addMensaje("No se encontro el movil para poder cruzar la informacion. Dominio: " + dominio);
                logger.error("No se encontro el movil para poder cruzar la informacion. Dominio: " + dominio);
                continue;
            }

            String numeroActa = getStringValorCelda(row.getCell(3));

            if(numeroActa == null){
                logImportacion.addMensaje("No se encontro el numero de acta. Row numero : " + row.getRowNum());
                logger.error("No se encontro el numero de acta. Row numero : " + row.getRowNum());
                continue;
            }

            if(numeros.contains(numeroActa.trim())){
                logImportacion.addMensaje("Numero de acta repetido. " + numeroActa);
                continue;
            }else{
                numeros.add(numeroActa.trim());
            }

            Infraccion infraccion = infraccionService.buscarPorNumeroActa(numeroActa.trim()).orElse(new Infraccion());

            infraccion.setNumero(numeroActa);

            Cell cellFechaActua = row.getCell(6);
            try {
                if(cellFechaActua.getDateCellValue()!=null){
                    if(!cellFechaActua.getCellType().equals(CellType.NUMERIC)){
                        infraccion.setFechaActa(getLocalDateFromExcel(cellFechaActua.getDateCellValue()));
                    }else{
                        infraccion.setFechaActa(getLocalDateFromExcelNumeric(cellFechaActua.getNumericCellValue()));
                    }
                }

            }catch (IllegalStateException e){
                logImportacion.addMensaje("Fecha de acta invalida, Acta NRO :  " + numeroActa);
                logger.error("Fecha de acta invalida, Acta NRO :  " + numeroActa);
            }

            infraccion.setMovil(movil.get());
            infraccion.setImporte(getBigDecimalFromExcel(row.getCell(10)));
            infraccion.setMotivo(getStringValorCelda(row.getCell(11)));
            infraccion.setAsignado(getStringValorCelda(row.getCell(15)));
            infraccion.setFormaPago(getStringValorCelda(row.getCell(17)));
            infraccion.setEstado(EstadoInfraccion.getEstadoParaImportacion(getStringValorCelda(row.getCell(20))));

            infraccionList.add(infraccion);
        }
        infraccionService.saveAll(infraccionList);
    }


    /**
     * Ingresa la informacion de los moviles existentes.
     *
     * @param sheet
     * @param logImportacion
     */
    private void insertarFomatoMoviles(Sheet sheet, LogImportacion logImportacion) {
        logImportacion.addMensaje("INICIO FORMATO MOVILES");
        List<Movil> moviles = new ArrayList<>();
        for(Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            String dominio = getStringValorCelda(row.getCell(1));

            if(dominio  == null){
                logImportacion.addMensaje("Dominio no encontrado.");
                logger.error("Dominio no encontrado.");
                continue;
            }

            Movil movil = movilService.getByDominio(dominio).orElse(new Movil());

            movil.setNumero(getStringValorCelda(row.getCell(0)));
            movil.setDominio(dominio);
            movil.setEstado(EstadoMovil.getEstadoMovilImportacion(getStringValorCelda(row.getCell(2))));
            movil.setAsignado(getStringValorCelda(row.getCell(3)));
            movil.setDestino(getStringValorCelda(row.getCell(4)));
            movil.setGerencia(Gerencia.getGerencia(row.getCell(5)));

            String dniEmpleado = getStringValorCelda(row.getCell(6));
            if(dniEmpleado != null){
                Optional<Empleado> empleado = empledoService.findByDNI(dniEmpleado);
                if(empleado.isPresent()){
                    movil.setEmpleado(empleado.get());
                }else{
                    logImportacion.addMensaje("Empleado no encontrado. DNI : " + dniEmpleado);
                    logger.error("Empleado no encontrado. DNI : " + dniEmpleado);
                }
            }
            moviles.add(movil);
        }

        movilService.saveAll(moviles);

    }

    /**
     * Ingresa la grilla de un empleado.
     *
     * @param sheet
     * @param logImportacion
     */
    private void insertarFormatoGrilla(Sheet sheet, LogImportacion logImportacion) {
        logImportacion.addMensaje("INICIO FORMATO GRILLA");
        Cell primeraFecha = sheet.getRow(0).getCell(1);
        if(primeraFecha == null){
            logImportacion.addMensaje("Primera fecha no encontrada.");
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
                logImportacion.addMensaje("DNI no ENCONTRADO. row:"+ row.getRowNum());
                continue;
            }
            Optional<Empleado> empleadoOpt = empledoService.findByDNI(dni);

            if(!empleadoOpt.isPresent()){
                logger.error("Empleado no encontrado : " + dni);
                logImportacion.addMensaje("Empleado no encontrado : " + dni);
                continue;
            }
            Integer cantidadDias = 0;
            List<Dia> diasIngresados = new ArrayList<>();
            for (Cell cell : row) {
                if(cell.getColumnIndex()>2 && cell.getColumnIndex()<33){
                    String estadoString = getStringValorCelda(cell);
                    EstadoDia estado =  EstadoDia.getEstadoDiaImportacion(estadoString);
                    LocalDate fechaAplica = fechaInicio.plusDays(cantidadDias);
                    if(estado!=null){
                        Dia dia = diaService.buscarPorFechaYEmpleado(fechaAplica , empleadoOpt.get()).orElse(new Dia());
                        dia.setEmpleado(empleadoOpt.get());
                        dia.setFecha(fechaAplica);
                        dia.setEstado(estado);
                        diasIngresados.add(dia);
                    }else{
                        Optional<Dia> registroViejo = diaService.buscarPorFechaYEmpleado(fechaAplica , empleadoOpt.get());
                        if(registroViejo.isPresent()){
                            diaService.delete(registroViejo.get());
                        }
                    }
                    if(estadoString != null && estado == null){
                        logger.error("ESTADO NO RECONOCIDO : " + estadoString);
                        logImportacion.addMensaje("ESTADO NO RECONOCIDO : " + estadoString);
                    }
                    cantidadDias++;
                }
            }
            diaService.saveAll(diasIngresados);
        }

    }

    /**
     * Ingresa las ofertas de empleo.
     *
     * @param sheet
     * @param logImportacion
     */
    private void insertarFomatoOfertasEmpleo(Sheet sheet, LogImportacion logImportacion) {
        logImportacion.addMensaje("INICIO Formato ofertas empleo.");
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
                logImportacion.addMensaje("Empresa o codigo no encontrado fila nro: " + row.getRowNum());
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
    private void insertarFomatoCredenciales(Sheet sheet, LogImportacion logImportacion) {
        logImportacion.addMensaje("INICIO Formato Credenciales.");
        for(Row row : sheet){
            if (row.getRowNum() == 0) {
                continue;
            }
            Cell celdaDni = row.getCell(3);
            if(celdaDni == null){
                logImportacion.addMensaje("DNI no encontrado, fila : " + row.getRowNum());
                continue;
            }
            celdaDni.setCellType(CellType.STRING);
            String dni = celdaDni.getStringCellValue().trim();
            if(dni.isEmpty()){
                logImportacion.addMensaje("DNI no encontrado, fila : " + row.getRowNum());
                continue;
            }
            Optional<Empleado> empleadoOpt = empledoService.findByDNI(dni);

            if(!empleadoOpt.isPresent()){
                logger.error("Empleado no encontrado, DNI : " + dni);
                logImportacion.addMensaje("Empleado no encontrado, DNI : " + dni);
                continue;
            }
            Empleado empleado = empleadoOpt.get();
            Cell celGerencia = row.getCell(8);
            Gerencia gerencia = Gerencia.getGerencia(celGerencia);

            Cell celJurisdiccion = row.getCell(7);
            Jurisdiccion jurisdiccion = Jurisdiccion.getJurisdiccion(getStringValorCelda(celJurisdiccion));

            if(gerencia == null || jurisdiccion == null){
                logger.error("Jurisdiccion o generencia invalida, DNI : " + dni);
                logImportacion.addMensaje("Jurisdiccion o generencia invalida, DNI : " + dni);
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
            credencial.setTipo(TipoCredencial.getTipoCredencialImportacion(getStringValorCelda(cellTipo)));
            Cell cellVencimiento = row.getCell(6);
            try {
                if(cellVencimiento != null && cellVencimiento.getDateCellValue()!=null){
                    if(!cellVencimiento.getCellType().equals(CellType.NUMERIC)){
                        credencial.setFechaVencimentoCredencial(getLocalDateFromExcel(cellVencimiento.getDateCellValue()));
                    }else{
                        credencial.setFechaVencimentoCredencial(getLocalDateFromExcelNumeric(cellVencimiento.getNumericCellValue()));
                    }
                }

            }catch (IllegalStateException e){
                logImportacion.addMensaje("Fecha de vencimiento invalida, DNI: " + dni);
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
     *
     * @param sheet
     * @param logImportacion
     */
    private void insertarFormatoSueldo(Sheet sheet, LogImportacion logImportacion) {
        logImportacion.addMensaje("INICIO FORMATO SUELDO" );
        List<Empleado> lista = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() >= 0 && row.getRowNum() <= 3) {
                continue;
            }
            Double sueldoTotal = Double.valueOf(0);
            Cell celdaDni = row.getCell(3);

            if(celdaDni == null){
                logImportacion.addMensaje("DNI no encontrado, fila : " + row.getRowNum());
                continue;
            }

            String dni = celdaDni.getStringCellValue().substring(3, 11);
            if(dni.isEmpty()){
                logImportacion.addMensaje("DNI no encontrado, fila : " + row.getRowNum());
                continue;
            }
            Optional<Empleado> empleadoOpt = empledoService.findByDNI(dni);
            if(empleadoOpt.isPresent()){
                Empleado empleado = empleadoOpt.get();
                for (Cell cell : row) {
                    if(cell != null){
                        try {
                            if (cell.getColumnIndex() > 10) {
                                try {
                                    sueldoTotal += cell.getNumericCellValue();
                                } catch (Exception e) {
                                    logImportacion.addMensaje("Dato incorrecto a sumar : fila y columna = " + row.getRowNum() + "  " + cell.getColumnIndex());
                                    logger.error("Dato invalido para sumar al sueldo total");
                                }
                            }
                        } catch (Exception e) {
                            logImportacion.addMensaje("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex());
                            logger.error("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex() );
                        }
                    }

                }
                if(empleado!=null){
                    empleado.setSueldoTotal(new BigDecimal(sueldoTotal));
                    lista.add(empleado);
                }
            }else{
                logImportacion.addMensaje("Empleado no encontrado, dni: " + dni);
            }
        }
        empledoService.saveAll(lista);

    }

    /**
     * Inserta toda la informacion basica del empleado.
     *
     * @param sheet
     * @param logImportacion
     */
    private void insertarFormatoLegajo(Sheet sheet, LogImportacion logImportacion) {
        logImportacion.addMensaje("INICIO Formato Legajo.");
        List<Empleado> listaEmpleados = new ArrayList<>();
        Set<String> dnis = new HashSet<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Cell celdaDni = row.getCell(41);
            celdaDni.setCellType(CellType.STRING);

            if(celdaDni == null) {
                logImportacion.addMensaje("DNI no encontrado, fila : " + row.getRowNum());
                continue;
            }
            String dni = celdaDni.getStringCellValue().trim();
            if(dni.isEmpty()){
                logImportacion.addMensaje("DNI no encontrado, fila : " + row.getRowNum());
                continue;
            }

            if(dnis.contains(dni.trim())){
                logImportacion.addMensaje("DNI Repetido, fila : " + row.getRowNum());
                continue;
            }else{
                dnis.add(dni.trim());
            }
            Empleado empleado = empledoService.findByDNI(dni).orElse(new Empleado(dni));
            Gerencia gerencia = null;
            for (Cell cell : row) {
                if(cell != null){
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
                                gerencia = Gerencia.getGerencia(cell);
                                if(gerencia!=null){
                                    empleado.setGerencia(gerencia);
                                }
                                break;
                            case 45:
                                Sindicato s = null;
                                try {
                                    s = Sindicato.getSindicatoImportacion(cell.getStringCellValue());
                                }catch (IllegalArgumentException e){
                                    logImportacion.addMensaje("Sindicato no reconocidog : " + row.getRowNum());
                                    logger.error("Sindicato no reconocido : " + cell.getStringCellValue());
                                }
                                empleado.setSindicato(s);
                                break;

                            case 46:
                                Optional<Empresa> empresa = Empresa.getByName(getStringValorCelda(cell));
                                if(empresa.isPresent()){
                                    empleado.setEmpresa(empresa.get());
                                }
                                break;

                            default:
                                //No me sirve el dato.
                        }
                    } catch (Exception e) {
                        logger.error("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex() );
                    }
                }

            }
            if(empleado!=null){
                listaEmpleados.add(empleado);
            }
        }
        empledoService.saveAll(listaEmpleados);
    }

    private void insertarFormatoBejerman(Sheet sheet, LogImportacion logImportacion) {
        logImportacion.addMensaje("INICIO IMPORTACION BEJERMAN");
        List<Empleado> lista = new ArrayList<>();
        Set<String> dnis = new HashSet<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Cell celdaDni = row.getCell(26);
            if(celdaDni == null ){
                logImportacion.addMensaje("DNI no encontrado, fila : " + row.getRowNum());
                continue;
            }
            celdaDni.setCellType(CellType.STRING);
            String dni = celdaDni.getStringCellValue();
            if(dni.isEmpty()){
                logImportacion.addMensaje("DNI no encontrado, fila : " + row.getRowNum());
                continue;
            }

            if(dnis.contains(dni.trim())){
                logImportacion.addMensaje("DNI REPETIDO, fila : " + row.getRowNum());
                continue;
            }else{
                dnis.add(dni.trim());
            }
            Empleado empleado = empledoService.findByDNI(dni).orElse(new Empleado(dni.trim()));
            Gerencia gerencia = null;
            for (Cell cell : row) {
                try {
                    if(cell != null){
                        switch (cell.getColumnIndex()) {
                            case 0:
                                gerencia = Gerencia.getGerencia(cell);
                                if(gerencia!=null){
                                    empleado.setGerencia(gerencia);
                                }
                                break;
                            case 1:
                                Optional<Empresa> empresa = Empresa.getByName(getStringValorCelda(cell));
                                if(empresa.isPresent()){
                                    empleado.setEmpresa(empresa.get());
                                }
                                break;
                            case 2:
                                empleado.setLegajoEmpleado(String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue()));
                                break;
                            case 3:
                                empleado.setNombreEmpleado(getNomnbreApellidoSplit(cell.getStringCellValue(), 1));
                                empleado.setApellidoEmpleado(getNomnbreApellidoSplit(cell.getStringCellValue(), 0));
                                break;
                            case 4:
                                cell.setCellType(CellType.STRING);
                                empleado.setTelefonoEmpleado(getStringSinEspacios(cell.getStringCellValue()));
                                break;
                            case 5:
                                empleado.setDireccionEmpleado(cell.getStringCellValue());
                                break;
                            case 12:
                                empleado.setCodigoPostalEmpleado(String.valueOf(cell.getNumericCellValue()));
                                break;
                            case 23:
                                empleado.setFechaNascimentoEmpleado(getLocalDateFromExcel(cell.getDateCellValue()));
                                break;
                            case 26:
                                cell.setCellType(CellType.STRING);
                                empleado.setDNIEmpleado(cell.getStringCellValue());
                                break;
                            case 30:
                                empleado.setObjetivoEmpleado(cell.getStringCellValue());
                                break;
                            case 32:
                                empleado.setCargoEmpleado(cell.getStringCellValue());
                                break;
                            case 67:
                                Sindicato s = null;
                                try {
                                    s = Sindicato.getSindicatoImportacion(cell.getStringCellValue());
                                }catch (IllegalArgumentException e){
                                    logImportacion.addMensaje("SINDICATO no reconocido : " + row.getRowNum());
                                    logger.error("Sindicato no reconocido : " + cell.getStringCellValue());
                                }
                                empleado.setSindicato(s);
                                break;
                            default:
                                //No me sirve el dato.
                        }
                    }

                } catch (Exception e) {
                    logger.error("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex() );
                    logImportacion.addMensaje("Error parseando empleado en fila  " + row.getRowNum() +  " y columna " +cell.getColumnIndex());
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
