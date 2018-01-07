/**
 * Clase Database contenedora de todo lo necesario para hacer consutas, updates... en la base de datos
 *
 * @author Deyvid Uzunov
 * @version 02/06/2017
 */
package Modelo;

import Controlador.MyError;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author DEYVID
 */
public class Database {

    private static Statement st;
    private static String db;
    private static String login;
    private static String password;
    private static String servidorMysql;
    private static Connection conexion;

    /**
     * Constructor con los datos para entrar a la base de datos.
     */
    public Database() {
        setDb("vehiculos");
        setLogin("User");
        setPassword("1234");
        setServidorMysql("jdbc:mysql://localhost/");
    }

    /**
     * Metodo para abrir la conexion con la base de datos.
     * @return true or false si se pudo conectar o no.
     * @throws Controlador.MyError si ocurre algun error
     */
    public static boolean abrirConexion() throws MyError {
        boolean estado = false;
        try {
            //cargar el driver
            Class.forName("com.mysql.jdbc.Driver");
            //crear la conexion con la base de datos
            setConexion(DriverManager.getConnection(getServidorMysql() + getDb(), getLogin(), getPassword()));
            estado = true;
        } catch (ClassNotFoundException ex) {
            throw new MyError("Error con el driver: "+ex.getMessage());
        } catch (SQLException ex) {

            throw new MyError("Error al conectarse al la base de datos, "+ex.getMessage()+ " codigo:"+ex.getErrorCode());
        }
        return estado;
    }

    /**
     * Metodo para cerrar la conexion con la base de datos.
     * @throws Controlador.MyError si ocurre algun error
     */
    public static void cerrarConexion() throws MyError {
        try {
            if (getConexion() != null) {
                getConexion().close();
            }
        } catch (SQLException ex) {
            throw new MyError("Error al cerrar la base de datos, "+ex.getMessage()+ " codigo:"+ex.getErrorCode());

        }
    }

    /**
     * Metodo para dar de alta un propietario
     * @param a propietario nuevo
     * @throws MyError si ocurre algun error
     */
    public static void ejecutaUpdateAltaProp(Propietario a) throws MyError {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement("insert into propietario(dni,nombre,apellido,telefono,provincia)"
                    + " values (?,?,?,?,?);");
            ps.setString(1, a.getDni());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getApellido());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getProvincia());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Metodo para dar de alta un coche
     * @param a nuevo coche
     * @throws MyError si ocurre algun error
     */
    public static void ejecutaUpdateAltaCoche(Vehiculo a) throws MyError {
        PreparedStatement ps = null;
        try {
            System.out.println("conexion: " + conexion);
            ps = conexion.prepareStatement("insert into vehiculo (matricula,modelo,anio,propietario)"
                    + " values (?,?,?,?);");

            ps.setString(1, a.getMatricula());
            ps.setString(2, a.getModelo());
            ps.setInt(3, a.getAnio());
            ps.setString(4, a.getPropietario());
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new MyError("No se pudo crear el coche, "+ ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error: " + ex.getErrorCode() + " mensaje: " + ex.getMessage());
                    
                }
            }
        }
    }

    /**
     * Metodo para buscar un propietario y devolver sus datos.
     * @param a propietario a buscar
     * @return propietario encontrado
     * @throws MyError si ocurre algun error
     */
    public static Propietario buscaPropietario(Propietario a) throws MyError {
        PreparedStatement ps = null;
        Propietario p = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("select * from propietario"
                    + " where dni=?;");
            ps.setString(1, a.getDni());
            rs = ps.executeQuery();
            while (rs.next()) {
                p = new Propietario(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
            return p;
        } catch (SQLException ex) {
            throw new MyError("Error, codigo:" + ex.getErrorCode() + " texto: " + ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo:" + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo:" + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Metodo que recoge todos los propietarios que cumplan dos condiciones.
     * @param provincia condicion 
     * @param numCoches condicion
     * @return ArrayList con los propietarios
     * @throws MyError si ocurre un error
     */
    public static ArrayList<Propietario> listadoPropConTodo(String provincia, int numCoches) throws MyError {
        
        PreparedStatement ps = null;
        ArrayList<Propietario> listaProp = new ArrayList<>();
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("select p.dni, p.nombre, p.apellido, p.telefono, p.provincia, count(*) as numVehiculos "
                    + " from propietario p join vehiculo v"
                    + " on (p.dni=v.propietario)"
                    + " where p.provincia=?"
                    + " group by p.dni, p.nombre, p.telefono, p.provincia"
                    + " having count(*)=?"
                    + " order by count(*);");
            System.out.println(ps);
            ps.setString(1, provincia);
            ps.setInt(2, numCoches);
            rs = ps.executeQuery();
            while (rs.next()) {
                Propietario b;
                b = new Propietario(rs.getNString(1), rs.getNString(2), rs.getNString(3), rs.getNString(4), rs.getNString(5));
                listaProp.add(b);
            }
            return listaProp;
        } catch (SQLException ex) {
            MyError m = new MyError("Error: " + ex.getErrorCode() + " mensaje: " + ex.getMessage());
            throw m;
        } catch (Exception e) {
            throw new MyError("Error, texto: " + e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Metodo que recoge todos los propietarios que cumplan una condicion.
     * @param provincia condicion
     * @return ArrayList con los propietarios
     * @throws MyError si ocurre algun error
     */
    public static ArrayList<Propietario> listadoPropProvincia(String provincia) throws MyError {
        PreparedStatement ps = null;
        ArrayList<Propietario> listaProp = new ArrayList<>();
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("select p.dni, p.nombre, p.apellido, p.telefono, p.provincia, count(*) as numVehiculos "
                    + " from propietario p left OUTER join vehiculo v"
                    + " on (p.dni=v.propietario)"
                    + " where p.provincia=?"
                    + " group by p.dni, p.nombre, p.telefono, p.provincia"
                    + " order by count(*);");
            System.out.println(ps);
            ps.setString(1, provincia);
            rs = ps.executeQuery();
            while (rs.next()) {
                Propietario b;
                b = new Propietario(rs.getNString(1), rs.getNString(2), rs.getNString(3), rs.getNString(4), rs.getNString(5));
                listaProp.add(b);
            }
            return listaProp;
        } catch (SQLException ex) {
            MyError m = new MyError("Error: " + ex.getErrorCode() + " mensaje: " + ex.getMessage());
            throw m;
        } catch (Exception e) {
            throw new MyError("Error, texto: " + e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Metodo que recoge todos los propietarios que cumplan una condicion.
     * @param numCoches condicion
     * @return ArrayList con los propietarios
     * @throws MyError si ocurre algun error
     */
    public static ArrayList<Propietario> listadoPropNumCoches(int numCoches) throws MyError {
        PreparedStatement ps = null;
        ArrayList<Propietario> listaProp = new ArrayList<>();
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("select p.dni, p.nombre, p.apellido, p.telefono, p.provincia, count(*) as numVehiculos"
                    + " from propietario p right OUTER join vehiculo v"
                    + " on (p.dni=v.propietario)"
                    + " group by p.dni, p.nombre, p.telefono, p.provincia"
                    + " having count(*)=?"
                    + " order by count(*);");
            System.out.println(ps);
            ps.setInt(1, numCoches);
            rs = ps.executeQuery();
            while (rs.next()) {
                Propietario b;
                b = new Propietario(rs.getNString(1), rs.getNString(2), rs.getNString(3), rs.getNString(4), rs.getNString(5));
                listaProp.add(b);
            }
            return listaProp;
        } catch (SQLException ex) {
            MyError m = new MyError("Error: " + ex.getErrorCode() + " mensaje: " + ex.getMessage());
            throw m;
        } catch (Exception e) {
            throw new MyError("Error, texto: " + e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Metodo que devuelve todos los propietarios
     * @return ArrayList con los propietarios
     * @throws MyError si ocurre algun error
     */
    public static ArrayList<Propietario> listadoPropNormal() throws MyError {
        PreparedStatement ps = null;
        ArrayList<Propietario> listaProp = new ArrayList<>();
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("select * from propietario;");
            System.out.println(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                Propietario b;
                b = new Propietario(rs.getNString(1), rs.getNString(2), rs.getNString(3), rs.getNString(4), rs.getNString(5));
                listaProp.add(b);
            }
            return listaProp;
        } catch (SQLException ex) {
            MyError m = new MyError("Error: " + ex.getErrorCode() + " mensaje: " + ex.getMessage());
            throw m;
        } catch (Exception e) {
            throw new MyError("Error, texto: " + e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Metodo que devuelve los coches de un propietario.
     * @param p propietario
     * @return ArrayList con los vehiculos
     * @throws MyError si ocurre algun error
     */
    public static ArrayList<Vehiculo> listadoCochesPropietario(Propietario p) throws MyError {
        PreparedStatement ps = null;
        ArrayList<Vehiculo> listaVehiculo = new ArrayList<>();
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("select * from vehiculo v join propietario p"
                    + " on (p.dni=v.propietario)"
                    + " where p.dni=?;");
            ps.setString(1, p.getDni());
            rs = ps.executeQuery();
            while (rs.next()) {
                Vehiculo v;
                v = new Vehiculo(rs.getNString(1), rs.getNString(2), rs.getInt(3), rs.getNString(4));
                listaVehiculo.add(v);
            }
            return listaVehiculo;
        } catch (SQLException ex) {
            MyError m = new MyError("Error: " + ex.getErrorCode() + " mensaje: " + ex.getMessage());
            throw m;
        } catch (Exception e) {
            System.out.println("Algo salio mal");
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Metodo para eliminar los coches de un propietario.
     * @param a propietario
     * @throws MyError si ocurre un error
     */
    public static void eliminaCochesProp(Propietario a) throws MyError {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement("delete from vehiculo"
                    + " where propietario=?;");
            ps.setString(1, a.getDni());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Metodo que elimina a un propietario
     * @param a propietario
     * @throws MyError si ocurre un error
     */
    public static void eliminaPropietario(Propietario a) throws MyError {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement("delete from propietario"
                    + " where dni=?;");
            ps.setString(1, a.getDni());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new MyError("Error, No puedes borrar un popietario si tiene coches.");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Metodo que devuelve todos los vehiculos.
     * @return ArrayList con los vehiculos
     * @throws MyError si ocurre un error
     */
    public static ArrayList<Vehiculo> listaVehiculosCompleta() throws MyError {
        ArrayList<Vehiculo> lista = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("select * from vehiculo"
                    + " order by anio;");
            rs = ps.executeQuery();
            while (rs.next()) {
                Vehiculo v = new Vehiculo(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4));
                lista.add(v);
            }
            return lista;
        } catch (SQLException ex) {
            throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Metodo que cambia un coche de propietario
     * @param p propietario
     * @param v coche
     * @throws MyError si ocurre un error 
     */
    public static void cambiarPropietario(Propietario p, Vehiculo v) throws MyError {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement("update vehiculo "
                    + " set propietario=?"
                    + " where matricula=?;");
            ps.setString(1, p.getDni());
            ps.setString(2, v.getMatricula());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new MyError("Error, codigo: " + ex.getErrorCode() + " texto: " + ex.getMessage());
                }
            }
        }
    }

    public static void setSt(Statement st) {
        Database.st = st;
    }

    public static void setDb(String db) {
        Database.db = db;
    }

    public static void setLogin(String login) {
        Database.login = login;
    }

    public static void setPassword(String password) {
        Database.password = password;
    }

    public static void setServidorMysql(String servidorMysql) {
        Database.servidorMysql = servidorMysql;
    }

    public static void setConexion(Connection conexion) {
        Database.conexion = conexion;
    }

    public static Statement getSt() {
        return st;
    }

    public static String getDb() {
        return db;
    }

    public static String getLogin() {
        return login;
    }

    public static String getPassword() {
        return password;
    }

    public static String getServidorMysql() {
        return servidorMysql;
    }

    public static Connection getConexion() {
        return conexion;
    }

}
