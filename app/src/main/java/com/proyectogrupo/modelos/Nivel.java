package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.gestores.Utilidades;
import com.proyectogrupo.powerups.CajaBomba;
import com.proyectogrupo.powerups.MonedaRecolectable;
import com.proyectogrupo.powerups.PowerUp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Nivel {
    public static int scrollEjeY = 0;
    private Tile[][] mapaTiles;
    public List<PowerUp> powerups;
    private Context context = null;
    private int numeroNivel;
    private Fondo fondo;
    public Nave nave;
    public float orientacionPadX = 0;
    public float orientacionPadY = 0;

    public boolean inicializado;
    public int monedasRecogidas;


    public Nivel(Context context, int numeroNivel) throws Exception {
        inicializado = false;

        this.context = context;
        this.numeroNivel = numeroNivel;
        inicializar();

        inicializado = true;
    }

    public void inicializar() throws Exception {
        scrollEjeY = 0;
        monedasRecogidas = 0;
        powerups = new LinkedList<>();

        fondo = new Fondo(context, CargadorGraficos.cargarDrawable(context, R.drawable.fondo));
        this.inicializarMapaTiles();
    }

    private void aplicarReglasMovimiento() {

        int tileXnaveIzquierda
                = (int) (nave.x - (nave.ancho / 2 - 1)) / Tile.ancho;
        int tileXnaveDerecha
                = (int) (nave.x + (nave.ancho / 2 - 1 )) / Tile.ancho;

        int tileYnaveInferior
                = (int) (nave.y + (nave.altura / 2 - 1))/ Tile.altura;
        int tileYnaveCentro
                = (int) nave.y / Tile.altura;
        int tileYnaveSuperior
                = (int) (nave.y - (nave.altura / 2 - 1)) / Tile.altura;

        this.moverNaveHorizontal(tileXnaveIzquierda,tileXnaveDerecha,tileYnaveInferior,
                tileYnaveCentro,tileYnaveSuperior);
        this.moverNaveVertical(tileXnaveIzquierda,tileXnaveDerecha,tileYnaveInferior,
                tileYnaveCentro,tileYnaveSuperior);
    }

    private void moverNaveHorizontal(int tileXnaveIzquierda,int tileXnaveDerecha,
                                     int tileYnaveInferior,int tileYnaveCentro,int tileYnaveSuperior){
        if (nave.velocidadX > 0) {
            // Tengo un tile delante y es PASABLE
            // El tile de delante está dentro del Nivel
            if (tileXnaveDerecha + 1 <= anchoMapaTiles() - 1 &&
                    tileYnaveInferior <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXnaveDerecha + 1][tileYnaveInferior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveDerecha + 1][tileYnaveCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveDerecha + 1][tileYnaveSuperior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveDerecha][tileYnaveInferior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveDerecha][tileYnaveCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveDerecha][tileYnaveSuperior].tipoDeColision ==
                            Tile.PASABLE) {
                if(nave.velocidadX > 0)
                    nave.x += nave.velocidadX;

                // No tengo un tile PASABLE delante
                // o es el FINAL del nivel o es uno SOLIDO
            } else if (tileXnaveDerecha <= anchoMapaTiles() - 1 &&
                    tileYnaveInferior <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXnaveDerecha][tileYnaveInferior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveDerecha][tileYnaveCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveDerecha][tileYnaveSuperior].tipoDeColision ==
                            Tile.PASABLE) {

                // Si en el propio tile de la nave queda espacio para
                // avanzar más, avanzo
                int TilenaveBordeDerecho = tileXnaveDerecha * Tile.ancho + Tile.ancho;
                double distanciaX = TilenaveBordeDerecho - (nave.x + nave.ancho / 2);

                if (distanciaX > 0) {
                    double velocidadNecesaria = Math.min(distanciaX, nave.velocidadX);
                    nave.x += velocidadNecesaria;
                } else {
                    // Opcional, corregir posición
                    nave.x = TilenaveBordeDerecho - nave.ancho / 2;
                }
            }
        }

        // izquierda
        if (nave.velocidadX < 0) {
            // Tengo un tile detrás y es PASABLE
            // El tile de delante está dentro del Nivel
            if (tileXnaveIzquierda - 1 >= 0 &&
                    tileYnaveInferior <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXnaveIzquierda - 1][tileYnaveInferior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda - 1][tileYnaveCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda - 1][tileYnaveSuperior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveInferior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveSuperior].tipoDeColision ==
                            Tile.PASABLE) {

                if(nave.velocidadX < 0)
                    nave.x += nave.velocidadX;

                // No tengo un tile PASABLE detrás
                // o es el INICIO del nivel o es uno SOLIDO
            } else if (tileXnaveIzquierda >= 0 && tileYnaveInferior <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveInferior].tipoDeColision
                            == Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveCentro].tipoDeColision
                            == Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveSuperior].tipoDeColision
                            == Tile.PASABLE) {

                // Si en el propio tile del nave queda espacio para
                // avanzar más, avanzo
                int TilenaveBordeIzquierdo = tileXnaveIzquierda * Tile.ancho;
                double distanciaX = (nave.x - nave.ancho / 2) - TilenaveBordeIzquierdo;

                if (distanciaX > 0) {
                    double velocidadNecesaria = Utilidades.proximoACero(-distanciaX, nave.velocidadX);
                    nave.x += velocidadNecesaria;
                } else {
                    // Opcional, corregir posición
                    nave.x = TilenaveBordeIzquierdo + nave.ancho / 2;
                }
            }
        }
    }

    private void moverNaveVertical(int tileXnaveIzquierda,int tileXnaveDerecha,
                                     int tileYnaveInferior,int tileYnaveCentro,int tileYnaveSuperior){
        if (nave.velocidadY > 0) {
            // Tengo un tile delante y es PASABLE
            // El tile de delante está dentro del Nivel
            if (tileYnaveInferior + 1 <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXnaveDerecha][tileYnaveInferior + 1].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveInferior + 1].tipoDeColision ==
                            Tile.PASABLE){
                if(nave.velocidadY > 0)
                    nave.y += nave.velocidadY;

                // No tengo un tile PASABLE delante
                // o es el FINAL del nivel o es uno SOLIDO
            } else if (tileXnaveDerecha <= anchoMapaTiles() - 1 &&
                    tileYnaveInferior <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXnaveDerecha][tileYnaveInferior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveDerecha][tileYnaveCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveDerecha][tileYnaveSuperior].tipoDeColision ==
                            Tile.PASABLE) {

                // Si en el propio tile de la nave queda espacio para
                // avanzar más, avanzo
                int TilenaveBordeInferior= tileYnaveInferior * Tile.altura + Tile.altura;
                double distanciaY = TilenaveBordeInferior - (nave.y + nave.altura / 2);

                if (distanciaY > 0) {
                    double velocidadNecesaria = Math.min(distanciaY, nave.velocidadY);
                    nave.y += velocidadNecesaria;
                } else {
                    // Opcional, corregir posición
                    nave.y = TilenaveBordeInferior - nave.altura / 2;
                }
            }
        }
        // izquierda
        if (nave.velocidadY < 0) {
            // Tengo un tile detrás y es PASABLE
            // El tile de delante está dentro del Nivel
            if (tileYnaveSuperior - 1 >= 0 &&
                    mapaTiles[tileXnaveDerecha][tileYnaveSuperior - 1].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveSuperior - 1].tipoDeColision ==
                            Tile.PASABLE ){

                if(nave.velocidadY < 0)
                    nave.y += nave.velocidadY;

                // No tengo un tile PASABLE detrás
                // o es el INICIO del nivel o es uno SOLIDO
            } else if (tileXnaveIzquierda >= 0 && tileYnaveInferior <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveInferior].tipoDeColision
                            == Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveCentro].tipoDeColision
                            == Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveSuperior].tipoDeColision
                            == Tile.PASABLE) {

                // Si en el propio tile del nave queda espacio para
                // avanzar más, avanzo
                int TilenaveBordeSuperior= tileYnaveSuperior * Tile.altura;
                double distanciaY = (nave.y - nave.altura / 2) - TilenaveBordeSuperior;

                if (distanciaY > 0) {
                    double velocidadNecesaria = Utilidades.proximoACero(-distanciaY,nave.velocidadY);
                    nave.y += velocidadNecesaria;
                } else {
                    // Opcional, corregir posición
                    nave.y = TilenaveBordeSuperior + nave.altura / 2;
                }
            }
        }
    }



    public void actualizar(long tiempo) {
        if (inicializado) {
            nave.procesarOrdenes(orientacionPadX,orientacionPadY);
            nave.actualizar(tiempo);
            this.aplicarReglasMovimiento();
        }
    }

    public void dibujar(Canvas canvas) {
        if (inicializado) {
            fondo.dibujar(canvas);
            dibujarTiles(canvas);
            nave.dibujar(canvas);
            for(PowerUp p :powerups)
                p.dibujar(canvas);
        }
    }

    private void dibujarTiles(Canvas canvas) {
        // Calcular que tiles serán visibles en la pantalla
        // La matriz de tiles es más grande que la pantalla

        int tileYNave = (int) nave.y / Tile.altura;
        int izquierda = 0; //El primer tile

        int derecha = izquierda +
                (GameView.pantallaAncho / Tile.ancho) + 1;

        if (nave.y < altoMapaTiles() * Tile.altura + GameView.pantallaAlto * 0.3)
            if (nave.y + scrollEjeY > GameView.pantallaAlto * 0.7) {
                scrollEjeY = (int) ((nave.y) - GameView.pantallaAlto * 0.7);
            }

        if (nave.y > GameView.pantallaAlto * 0.3)
            if (nave.y + scrollEjeY < GameView.pantallaAlto * 0.3) {
                scrollEjeY = (int) (nave.y - GameView.pantallaAlto * 0.3);
            }

        // el ultimo tile visible
        derecha = Math.min(derecha, anchoMapaTiles() - 1);

        for (int y = 0; y < altoMapaTiles(); ++y) {
            for (int x = izquierda; x <= derecha; ++x) {
                if (mapaTiles[x][y].imagen != null) {
                    // Calcular la posición en pantalla correspondiente
                    // izquierda, arriba, derecha , abajo

                    mapaTiles[x][y].imagen.setBounds(
                            x * Tile.ancho,
                            y * Tile.altura - scrollEjeY,
                            x * Tile.ancho + Tile.ancho,
                            y * Tile.altura + Tile.altura - scrollEjeY);

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
                nave = new Nave(context,xCentroAbajoTile,yCentroAbajoTile);
            case 'm':
                int xCentroAbajoTileMoneda= x * Tile.ancho + Tile.ancho/2;
                int yCentroAbajoTileMoneda = y * Tile.altura + Tile.altura;
                Log.d("MONEDA POSICION","x: "+xCentroAbajoTileMoneda+", y: "+yCentroAbajoTileMoneda);
                powerups.add(new MonedaRecolectable(context,xCentroAbajoTileMoneda,yCentroAbajoTileMoneda));
            case '.':
                // en blanco, sin textura
                return new Tile(null, Tile.PASABLE);
            case '#':
                // bloque de musgo, no se puede pasar
                return new Tile(CargadorGraficos.cargarDrawable(context,
                        R.drawable.blocka2), Tile.SOLIDO);
            case 'B':
                powerups.add(new CajaBomba(context, x,y));
            default:
                //cualquier otro caso
                return new Tile(null, Tile.PASABLE);
        }
    }

    private float tilesEnDistanciaY(double distanciaY) {
        return (float) distanciaY / Tile.altura;
    }
}

