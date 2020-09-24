package com.fm.modules.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fm.modules.R;
import com.fm.modules.app.login.Logon;
import com.fm.modules.app.login.Logued;
import com.fm.modules.app.pedidos.Principal;
import com.fm.modules.entities.RespuestaPedidosDriver;
import com.fm.modules.models.Driver;
import com.fm.modules.models.Menu;
import com.fm.modules.models.Pedido;
import com.fm.modules.models.RespuestaGenerica;
import com.fm.modules.models.Restaurante;
import com.fm.modules.models.Usuario;
import com.fm.modules.service.DriverService;
import com.fm.modules.service.PedidoService;
import com.fm.modules.service.RestauranteService;
import com.fm.modules.service.UsuarioService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerPedidosAdapter extends RecyclerView.Adapter<RecyclerPedidosAdapter.ViewHolder> {

    List<RespuestaPedidosDriver> pedidosList;
    Context context;

    SimpleDateFormat ffecha = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat fhora = new SimpleDateFormat("HH:mm:ss");

    RespuestaPedidosDriver resObt;
    Restaurante buscarRestaurante = new Restaurante();
    Driver buscarDriver = new Driver();
    Usuario buscarUsuario = new Usuario();

    ObtenerRestaurante obtenerRestaurante = new ObtenerRestaurante();
    ObtenerDriver obtenerDriver = new ObtenerDriver();
    ObtenerUsuario obtenerUsuario = new ObtenerUsuario();
    ActualizarPedido actualizarPedido = new ActualizarPedido();

    Pedido upPedido = new Pedido();
    RespuestaPedidosDriver upRespuestaPedidoDriver = new RespuestaPedidosDriver();

    public RecyclerPedidosAdapter(Context context, List<RespuestaPedidosDriver> pedidosList){
        this.context = context;
        this.pedidosList = pedidosList;
    }

    @NonNull
    @Override
    public RecyclerPedidosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_pedidos, parent, false);
        return new RecyclerPedidosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerPedidosAdapter.ViewHolder holder, int position) {
        //if(this.pedidosList.isEmpty()){
        //    Toast.makeText(this.context, "No tienes pedidos a entregar", Toast.LENGTH_SHORT).show();
        //}else{
            holder.asignarDatos(pedidosList.get(position));
        //}

    }

    @Override
    public int getItemCount() {
        return pedidosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvNombreRestaurante;
        AppCompatTextView tvDireccionCliente;
        AppCompatTextView tvPrecioPedido;
        AppCompatTextView btnTomarPedido;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombreRestaurante = itemView.findViewById(R.id.tvNombreRestaurante);
            tvDireccionCliente = itemView.findViewById(R.id.tvDirecionCliente);
            tvPrecioPedido = itemView.findViewById(R.id.tvPrecioPedido);
            btnTomarPedido = itemView.findViewById(R.id.btnTomarPedido);
        }

        public void asignarDatos(final RespuestaPedidosDriver res){
            tvNombreRestaurante.setText(res.getRestaurante());
            tvDireccionCliente.setText(res.getDireccion());
            tvPrecioPedido.setText("$" +String.valueOf(res.getTotalEnRestautante()));
            btnTomarPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog(res);
                }
            });
        }

        public void dialog(final RespuestaPedidosDriver res){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Pedido");
            builder.setMessage("¿Desea tomar este pedido?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //RestauranteService restauranteService = new RestauranteService();
                    //Restaurante restaurante = restauranteService.obtenerRestaurantePorId(res.getRestauranteId());
                    buscarRestaurante.setRestauranteId(res.getRestauranteId());
                    buscarDriver.setDriverId(res.getDriverId());
                    buscarUsuario.setUsuarioId(res.getUsuarioId());

                    resObt = res;

                    obtenerRestaurante.execute();
                    obtenerDriver.execute();
                    obtenerUsuario.execute();
                    actualizarPedido.execute();

                    Intent intent = new Intent(context, Principal.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    //reiniciarAsynkProcess();
                    Toast.makeText(context, "Ha decidido tomar este pedido..", Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(context, "No tomocesa este pedido..", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
    }

    public void reiniciarAsynkProcess() {
        obtenerRestaurante.cancel(true);
        obtenerRestaurante = new ObtenerRestaurante();

        obtenerDriver.cancel(true);
        obtenerDriver = new ObtenerDriver();

        obtenerUsuario.cancel(true);
        obtenerUsuario = new ObtenerUsuario();

        actualizarPedido.cancel(true);
        actualizarPedido = new ActualizarPedido();
    }


    public class ObtenerRestaurante extends AsyncTask<String, String, List<Restaurante>> {

        @Override
        protected List<Restaurante> doInBackground(String... strings) {
            List<Restaurante> restaurantes = new ArrayList<>();
            //Driver driver = Logued.driverLogued;
            try {
                RestauranteService restauranteService = new RestauranteService();
                Restaurante restaurante = restauranteService.obtenerRestaurantePorId(buscarRestaurante.getRestauranteId());
                buscarRestaurante = restaurante;

            }catch (Exception e){
                System.out.println("Error en UnderThreash ObtenerRestaurante:" +e.getMessage() +" " +e.getClass());
            }
            return restaurantes;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Restaurante> restaurantes) {
            super.onPostExecute(restaurantes);
            try {
                if (!restaurantes.isEmpty()){

                }else{
                    //Toast.makeText(context, "Pedidos No Cargados" +restaurantes.size(), Toast.LENGTH_SHORT).show();
                }
                //reiniciarAsynkProcess();
                //Toast.makeText(context, "Restaurante: " +buscarRestaurante.getRepresentante(), Toast.LENGTH_SHORT).show();
            }catch (Throwable throwable){
                System.out.println("Error Activity: " +throwable.getMessage());
                throwable.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

    public class ObtenerDriver extends AsyncTask<String, String, List<Driver>> {

        @Override
        protected List<Driver> doInBackground(String... strings) {
            List<Driver> drivers = new ArrayList<>();
            //Driver driver = Logued.driverLogued;
            try {
                DriverService driverService = new DriverService();
                Driver driver = driverService.obtenerDriverPorId(buscarDriver.getDriverId());
                buscarDriver = driver;

            }catch (Exception e){
                System.out.println("Error en UnderThreash ObtenerDriver:" +e.getMessage() +" " +e.getClass());
            }
            return drivers;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Driver> drivers) {
            super.onPostExecute(drivers);
            try {
                if (!drivers.isEmpty()){

                    Toast.makeText(context, "Pedidso Cargados" +drivers.size(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Pedidos No Cargados" +drivers.size(), Toast.LENGTH_SHORT).show();
                }
                //reiniciarAsynkProcess();
            }catch (Throwable throwable){
                System.out.println("Error Activity: " +throwable.getMessage());
                throwable.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

    public class ObtenerUsuario extends AsyncTask<String, String, List<Usuario>> {

        @Override
        protected List<Usuario> doInBackground(String... strings) {
            List<Usuario> usuarios = new ArrayList<>();
            Driver driver = Logued.driverLogued;
            try {
                UsuarioService usuarioService = new UsuarioService();
                Usuario usuario = usuarioService.obtenerUsuarioPorId(buscarUsuario.getUsuarioId());
                buscarUsuario = usuario;

            }catch (Exception e){
                System.out.println("Error en UnderThreash ObtenerUsuario:" +e.getMessage() +" " +e.getClass());
            }
            return usuarios;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Usuario> usuarios) {
            super.onPostExecute(usuarios);
            try {
                if (!usuarios.isEmpty()){

                    Toast.makeText(context, "Usuario Cargados" +usuarios.size(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Usuario No Cargados" +usuarios.size(), Toast.LENGTH_SHORT).show();
                    //reiniciarAsynkProcess();
                }
                //reiniciarAsynkProcess();
            }catch (Throwable throwable){
                System.out.println("Error Activity: " +throwable.getMessage());
                throwable.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

    public class ActualizarPedido extends AsyncTask<String, String, List<Pedido>> {

        @Override
        protected List<Pedido> doInBackground(String... strings) {
            List<Pedido> usuarios = new ArrayList<>();
            Driver driver = Logued.driverLogued;
            try {
                PedidoService pedidoService = new PedidoService();

                upPedido.setPedidoId(resObt.getPedidoId());
                upPedido.setRestaurante(buscarRestaurante);
                upPedido.setDrivers(buscarDriver);
                upPedido.setUsuario(buscarUsuario);
                upPedido.setStatus(3);
                System.out.println("Statussss" +upPedido.getStatus());
                upPedido.setFormaDePago(resObt.getFormaDePago());
                upPedido.setTotalDePedido(resObt.getTotalDePedido());
                upPedido.setTotalEnRestautante(resObt.getTotalEnRestautante());
                upPedido.setTotalDeCargosExtra(resObt.getTotalDeCargosExtra());
                upPedido.setTotalEnRestautanteSinComision(resObt.getTotalEnRestautanteSinComision());
                upPedido.setPedidoPagado(resObt.isPedidoPagado());
                System.out.println("Fecha: " +resObt.getFechaOrdenado().toString() +"\n Tiempo: " +resObt.getTiempoPromedioEntrega().toString());
                upPedido.setFechaOrdenado(resObt.getFechaOrdenado());
                upPedido.setTiempoPromedioEntrega(resObt.getTiempoPromedioEntrega());
                upPedido.setPedidoEntregado(false);
                upPedido.setNotas(resObt.getNotas());
                upPedido.setTiempoAdicional(resObt.getTiempoAdicional());
                upPedido.setDireccion(resObt.getDireccion());

                System.out.println(upPedido.toString());
                System.out.println(upPedido.getStatus());
                pedidoService.actualizarPedidoPorId(upPedido);

                reiniciarAsynkProcess();

            }catch (Exception e){
                System.out.println("Error en UnderThreash ActualizarPedidoce:" +e.getMessage() +" " +e.getClass());
                System.out.println(upPedido.toString());
            }
            return usuarios;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Pedido> pedidos) {
            super.onPostExecute(pedidos);
            try {
                reiniciarAsynkProcess();
            }catch (Throwable throwable){
                System.out.println("Error Activity: " +throwable.getMessage());
                throwable.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}
