package com.example.d_personales;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;

public class HelloController {
    @FXML
    private TextField textProfession;
    @FXML
    private TextField textEdad;
    @FXML
    private TextField textApellido;
    @FXML
    private TextField textNombre;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField textId;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Salio bien");
    }

    public void onBottonIngresar(ActionEvent actionEvent ) {
        ingresardatos();
        textId.setText("");
        textNombre.setText("");
        textApellido.setText("");
        textEdad.setText("");
        textProfession.setText("");
    }
    public void onBottonVer(ActionEvent actionEvent) {
        escribirdatos();
    }
    public void onBottonBorrar(ActionEvent actionEvent) {
        borrardatos();
        textId.setText("");
    }
    public void onBottonActualizar(ActionEvent actionEvent) {
        actualizardatos();
        textId.setText("");
    }
    public void establecerConexion(){
        String DB_url = "jdbc:mysql://localhost/DatosPersonales";
        String usuario = "root";
        String contrasena = "root_bas3";
        String query = "Select * from IngresoDatos";
        try {
            Connection conectar = DriverManager.getConnection(DB_url,usuario,contrasena);
            Statement stmt = conectar.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("conexion exitosa");
            while(rs.next()){
                System.out.println("\nid: "+rs.getString("id"));
                System.out.println("nombre: "+rs.getString("nombre"));
                System.out.println("apellido: "+rs.getString("apellido"));
                System.out.println("edad: "+rs.getBigDecimal("edad"));
                System.out.println("profesion: "+rs.getString("profesion"));
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);

        }
    }
    public void ingresardatos() {

        try {
            String DB_url = "jdbc:mysql://localhost/DatosPersonales";
            String usuario = "root";
            String contrasena = "root_bas3";
            String Sid = textId.getText().toString();
            String Snombre = textNombre.getText().toString();
            String Sapellido = textApellido.getText().toString();
            String Sedad = textEdad.getText();
            String Sprofesion = textProfession.getText().toString();
            int numero = Integer.parseInt(Sedad);

            String query = "insert into IngresoDatos (id, nombre, apellido, edad, profesion) values (?,?,?,?,?)";

            Connection conectar = DriverManager.getConnection(DB_url,usuario,contrasena);
            PreparedStatement statement = conectar.prepareStatement(query);
            statement.setString(1, Sid);
            statement.setString(2, Snombre);
            statement.setString(3,Sapellido);
            statement.setInt(4, numero);
            statement.setString(5, Sprofesion);
            statement.executeUpdate();
            statement.close();
            conectar.close();
            welcomeText.setText("Ingreso exitoso");
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    public void borrardatos(){
        try {
            String DB_url = "jdbc:mysql://localhost/DatosPersonales";
            String usuario = "root";
            String contrasena = "root_bas3";
            String idBorrar = textId.getText().toString();

            String query = "delete from IngresoDatos where id = ?";

            Connection conectar = DriverManager.getConnection(DB_url,usuario,contrasena);
            PreparedStatement statement = conectar.prepareStatement(query);
            statement.setString(1, idBorrar);
            int filasafectadas = statement.executeUpdate();


            if (filasafectadas > 0){
                welcomeText.setText("Eliminación exitosa");
            } else {
                welcomeText.setText("No se encontró ningun dato en el ID proporcionado");
            }

            statement.close();
            conectar.close();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void escribirdatos(){
        try {
            String DB_url = "jdbc:mysql://localhost/DatosPersonales";
            String usuario = "root";
            String contrasena = "root_bas3";
            String idRecuperar = textId.getText().toString();
            String arg = "Select * from IngresoDatos where id = "+ idRecuperar + ";";
            String query = arg;

            Connection conectar = DriverManager.getConnection(DB_url,usuario,contrasena);
            Statement stmt = conectar.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()){
                String nombreRec = rs.getString("nombre");
                String apellidoRec = rs.getString("apellido");
                int edadRec = rs.getInt("edad");
                String profesionRec = rs.getString("profesion");

                textNombre.setText(nombreRec);
                textApellido.setText(apellidoRec);
                textEdad.setText(String.valueOf(edadRec));
                textProfession.setText(profesionRec);

                welcomeText.setText("");
            } else {
                welcomeText.setText("No se encontró ningun dato en el ID proporcionado");
            }

            stmt.close();
            rs.close();
            conectar.close();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void actualizardatos(){
        try {
            String DB_url = "jdbc:mysql://localhost/DatosPersonales";
            String usuario = "root";
            String contrasena = "root_bas3";
            String Sid = textId.getText().toString();
            String Snombre = textNombre.getText().toString();
            String Sapellido = textApellido.getText().toString();
            String Sedad = textEdad.getText();
            String Sprofesion = textProfession.getText().toString();
            int numero = Integer.parseInt(Sedad);

            String query = "Update IngresoDatos set nombre = ?, apellido = ?, edad = ?, profesion = ? where id = ?";

            Connection conectar = DriverManager.getConnection(DB_url,usuario,contrasena);
            PreparedStatement upstatement = conectar.prepareStatement(query);

            upstatement.setString(1, Snombre);
            upstatement.setString(2,Sapellido);
            upstatement.setInt(3, numero);
            upstatement.setString(4, Sprofesion);
            upstatement.setString(5, Sid);

            int filasactualizada = upstatement.executeUpdate();

            if (filasactualizada > 0){
                welcomeText.setText("Actualizacion exitosa");
            }else {
                welcomeText.setText("No se encontró el ID proporcionado");
            }
            upstatement.close();
            conectar.close();

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}