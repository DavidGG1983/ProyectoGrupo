package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Nivel {
    private Tile[][] mapaTiles;
    private Context context = null;
    private int numeroNivel;
    private Fondo fondo;
    private Nave nave;

    public boolean inicializado;

    public Nivel(Context context, int numeroNivel) throws Exception {
        inicializado = false;

        this.context = context;
        this.numeroNivel = numeroNivel;
        inicializar();

        inicializado = true;
    }

    public void inicializar() throws Exception {
        fondo = new Fondo(context, CargadorGraficos.cargarDrawable(context, R.drawable.fondo));
        this.inicializarMapaTiles();
    }


    public void actualizar(long tiempo) {
        if (inicializado) {
            nave.actualizar(tiempo);
        }
    }

    public void dibujar(Canvas canvas) {
        if (inicializado) {
            fondo.dibujar(canvas);
            dibujarTiles(canvas);
            nave.dibujar(canvas);
        }
    }

    private void dibujarTiles(Canvas canvas) {
        // Calcular que tiles serán visibles en la pantalla
        // La matriz de tiles es más grande que la pantalla

        int izquierda = 0; //El primer tile

        int derecha = izquierda +
                (GameView.pantallaAncho / Tile.ancho) + 1;

        // el ultimo tile visible
        derecha = Math.min(derecha, anchoMapaTiles() - 1);

        for (int y = 0; y < altoMapaTiles(); ++y) {
            for (int x = izquierda; x <= derecha; ++x) {
                if (mapaTiles[x][y].imagen != null) {
                    // Calcular la posición en pantalla correspondiente
                    // izquierda, arriba, derecha , abajo

                    mapaTiles[x][y].imagen.setBounds(
                            x * Tile.ancho,
                            y * Tile.altura,
                            x * Tile.ancho + Tile.ancho,
                            y * Tile.altura + Tile.altura);

                    mapaTiles[x][y].imagen.draw(canvas);
                }
            }
        }
    }

    public int anchoMapaTiles() {
        return mapaTiles.length;
    }

    public int altoMapaTiles() {

        return mapaTiles[0].length;
    }

    private void inicializarMapaTiles() throws Exception {
        InputStream is;
        is = context.getAssets().open(numeroNivel + ".txt");
        int anchoLinea;

        List<String> lineas = new LinkedList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        {
            String linea = reader.readLine();
            anchoLinea = linea.length();
            while (linea != null) {
                lineas.add(linea);
                if (linea.length() != anchoLinea) {
                    Log.e("ERROR", "Dimensiones incorrectas en la línea");
                    throw new Exception("Dimensiones incorrectas en la línea.");
                }
                linea = reader.readLine();
            }
        }

        // Inicializar la matriz
        mapaTiles = new Tile[anchoLinea][lineas.size()];
        // Iterar y completar todas las posiciones
        for (int y = 0; y < altoMapaTiles(); ++y) {
            for (int x = 0; x < anchoMapaTiles(); ++x) {
                char tipoDeTile = lineas.get(y).charAt(x);//lines[y][x];
                mapaTiles[x][y] = inicializarTile(tipoDeTile, x, y);
            }
        }
    }

    private Tile inicializarTile(char codigoTile, int x, int y) {
        switch (codigoTile) {
            case '1':
                int xCentroAbajoTile = x * Tile.ancho + Tile.ancho/2;
                int yCentroAbajoTile = y * Tile.altura + Tile.altura;
                nave = new Nave(context,xCentroAbajoTile,yCentroAbajoTile);            case '.':
                // en blanco, sin textura
                return new Tile(null, Tile.PASABLE);
            case '#':
                // bloque de musgo, no se puede pasar
                return new Tile(CargadorGraficos.cargarDrawable(context,
                        R.drawable.blocka2), Tile.SOLIDO);
            default:
                //cualquier otro caso
                return new Tile(null, Tile.PASABLE);
        }
    }

}

