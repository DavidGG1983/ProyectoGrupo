package com.proyectogrupo.modelos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;

import com.proyectogrupo.DerrotaActivity;
import com.proyectogrupo.Dificultad;
import com.proyectogrupo.GameView;
import com.proyectogrupo.Hilo;
import com.proyectogrupo.R;
import com.proyectogrupo.Utils;
import com.proyectogrupo.VictoriaActivity;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.gestores.Utilidades;
import com.proyectogrupo.modelos.disparos.DisparoBomba;
import com.proyectogrupo.modelos.disparos.DisparoEnemigo;
import com.proyectogrupo.modelos.disparos.DisparoEnemigoLanzallamas;
import com.proyectogrupo.modelos.disparos.DisparoEnemigoRalentizador;
import com.proyectogrupo.modelos.disparos.DisparoHelicoptero;
import com.proyectogrupo.modelos.disparos.DisparoVista;
import com.proyectogrupo.modelos.enemigos.Disparador;
import com.proyectogrupo.modelos.enemigos.Enemigo;
import com.proyectogrupo.modelos.enemigos.EnemigoDisparador;
import com.proyectogrupo.modelos.enemigos.EnemigoLanzaBombas;
import com.proyectogrupo.modelos.enemigos.EnemigoLanzallamas;
import com.proyectogrupo.modelos.enemigos.EnemigoRalentizador;
import com.proyectogrupo.modelos.enemigos.EnemigoVista;
import com.proyectogrupo.powerups.CajaAleatoria;
import com.proyectogrupo.powerups.CajaBomba;
import com.proyectogrupo.powerups.CajaColor;
import com.proyectogrupo.powerups.CajaContraEnemigos;
import com.proyectogrupo.powerups.CajaDestruccion;
import com.proyectogrupo.powerups.CajaEnemigos;
import com.proyectogrupo.powerups.CajaInvulnerabilidad;
import com.proyectogrupo.powerups.CajaLentitud;
import com.proyectogrupo.powerups.CajaPuntosExtra;
import com.proyectogrupo.powerups.CajaSemiInvulnerabilidad;
import com.proyectogrupo.powerups.CajaVelocidad;
import com.proyectogrupo.powerups.CajaVidaExtra;
import com.proyectogrupo.powerups.MonedaRecolectable;
import com.proyectogrupo.powerups.PowerUp;
import com.proyectogrupo.powerups.Teletransporte;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Nivel {
    public static Dificultad dificultad;
    public static int scrollEjeY = 0;

    private Tile[][] mapaTiles;
    public List<PowerUp> powerups = new ArrayList<>();
    private Context context = null;
    public static boolean infinito;
    public static int numeroNivel;
    private Fondo fondo;
    public Nave nave;
    public float orientacionPadX = 0;
    public float orientacionPadY = 0;
    public List<Enemigo> enemigos = new ArrayList<>();
    private List<Helicoptero> helicopteros = new ArrayList<>();
    public List<Integer> coloresCajas = new ArrayList<>();
    private Enemigo enemigoColorCaja;
    private List<DisparoEnemigo> disparosEnemigos = new ArrayList<>();
    private List<DisparoHelicoptero> disparosHelicopteros = new ArrayList<>();

    private MarcadorPuntos marcadorPuntos;

    public boolean inicializado;
    public int monedasRecogidas;

    private int numMapa = 1;

    private double minPosNaveY;

    private long tiempoUltimoMovimientoNave = -1;
    private double ultimaPosYNave = -1;
    private int numEnemigosActual = 1;

    public Nivel(Context context) throws Exception {
        inicializado = false;

        this.context = context;
        inicializar();

        inicializado = true;
        minPosNaveY = nave.y;
    }

    public void inicializar() throws Exception {
        numEnemigosActual = 1;
        scrollEjeY = 0;
        minPosNaveY = 0;
        monedasRecogidas = 0;
        fondo = new Fondo(context, CargadorGraficos.cargarDrawable(context, R.drawable.fondo));
        marcadorPuntos = new MarcadorPuntos(context, 0.95 * GameView.pantallaAncho, 0.07 * GameView.pantallaAlto);
        this.inicializarMapaTiles();
    }

    private void aplicarReglasMovimiento() {

        int tileXnaveIzquierda
                = (int) (nave.x - (nave.ancho / 2 - 1)) / Tile.ancho;
        int tileXnaveDerecha
                = (int) (nave.x + (nave.ancho / 2 - 1)) / Tile.ancho;

        int tileYnaveInferior
                = (int) (nave.y + (nave.altura / 2 - 1)) / Tile.altura;
        int tileYnaveCentro
                = (int) nave.y / Tile.altura;
        int tileYnaveSuperior
                = (int) (nave.y - (nave.altura / 2 - 1)) / Tile.altura;

        this.moverNaveHorizontal(tileXnaveIzquierda, tileXnaveDerecha, tileYnaveInferior,
                tileYnaveCentro, tileYnaveSuperior);
        this.moverNaveVertical(tileXnaveIzquierda, tileXnaveDerecha, tileYnaveInferior,
                tileYnaveCentro, tileYnaveSuperior);
        this.moverEnemigos();
        this.moverHelicopteros();
        this.moverDisparos();
        this.colisionesPowerUps();
        this.colisionaEnemigos();
        this.colisionesDisparos();

        if (infinito) {
            realizarCambioMapaSiNecesario();
        }
    }

    private void realizarCambioMapaSiNecesario() {
        int tileYNave = (int) (nave.y / Tile.altura);
        Log.d("movnave", "" + tileYNave);
        if (tileYNave <= 14) {
            scrollEjeY = ((int) altoMapaTiles() * Tile.altura - GameView.pantallaAlto);
            int tileYDestino = altoMapaTiles() - 1 - (altoMapaTiles() / 2 - tileYNave);
            nave.y += (tileYDestino * Tile.altura - tileYNave * Tile.altura);
            nave.y += 1 * Tile.altura;
            //scrollEjeY += (tileYDestino * Tile.altura - tileYNave * Tile.altura);
            copiarMapaArribaAbajo();
            inicializarMapaTilesAleatorioArriba();
        }
    }

    private void copiarMapaArribaAbajo() {
        limpiarMapaAbajo();
        for (int y = altoMapaTiles() / 2; y < altoMapaTiles(); ++y) {
            for (int x = 0; x < anchoMapaTiles(); ++x) {
                mapaTiles[x][y] = mapaTiles[x][y - altoMapaTiles() / 2];
            }
        }

        moverModelosAbajo(powerups);
        moverModelosAbajo(enemigos);
        moverModelosAbajo(helicopteros);
        this.numMapa++;

            if (numEnemigosActual < 20) {
                numEnemigosActual++;
            }
    }

    private <T extends Modelo> void moverModelosAbajo(List<T> modelos) {
        for (T modelo : modelos) {
            if (!estaEnMapaDeAbajo(modelo)) {
                int tileYModelo = (int) (modelo.y / Tile.altura);
                int tileYFinalModelo = altoMapaTiles() - 1 - (altoMapaTiles() / 2 - 1 - tileYModelo);
                modelo.y = tileYFinalModelo * Tile.altura;
            }
        }
    }

    private void limpiarMapaAbajo() {
        limpiarModelosMapaAbajo(powerups);
        limpiarModelosMapaAbajo(enemigos);
        limpiarModelosMapaAbajo(helicopteros);
        limpiarModelosMapaAbajo(disparosEnemigos);
        limpiarModelosMapaAbajo(disparosHelicopteros);
    }

    private <T extends Modelo> void limpiarModelosMapaAbajo(List<T> modelos) {
        Iterator<T> iterador = modelos.iterator();
        while (iterador.hasNext()) {
            Modelo modelo = iterador.next();
            if (estaEnMapaDeAbajo(modelo)) {
                iterador.remove();
            }
        }
    }

    private boolean estaEnMapaDeAbajo(Modelo modelo) {
        int tileYModelo = (int) (modelo.y / Tile.altura);
        return tileYModelo < altoMapaTiles() && tileYModelo > altoMapaTiles() / 2;
    }

    private void moverHelicopteros() {
        Helicoptero helicopteroAEliminar = null;
        for (Helicoptero helicoptero : helicopteros) {
            if (helicoptero.x - helicoptero.ancho / 2 <= 0) {
                helicopteroAEliminar = helicoptero;
                break;
            }
        }

        if (helicopteroAEliminar != null) {
            helicopteros.remove(helicopteroAEliminar);
        }

        DisparoHelicoptero disparoABorrar = null;

        for (DisparoHelicoptero disparoHelicoptero : disparosHelicopteros) {
            if (disparoHelicoptero.y - Nivel.scrollEjeY >= GameView.pantallaAlto) {
                disparoABorrar = disparoHelicoptero;
            }
        }

        if (disparoABorrar != null) {
            disparosHelicopteros.remove(disparoABorrar);
        }
    }

    private void colisionesDisparos() {

        DisparoEnemigo aBorrar = null;
        for (DisparoEnemigo d : disparosEnemigos) {
            if ((d instanceof DisparoBomba && !((DisparoBomba) d).explotando))
                continue;
            if (d.colisiona(nave)) {
                if (!nave.invulnerable) {
                    if (!nave.contrataque) {
                        aBorrar = colisionDisparoNave(d);
                        break;
                    } else {
                        //Matar al enemigo que disparo
                        enemigos.remove(d.enemigo);
                        aBorrar = d;
                    }
                }
            } else {
                if (d instanceof DisparoBomba) {
                    DisparoBomba disparoBomba = (DisparoBomba) d;

                    if (Math.abs(nave.x - disparoBomba.x) <= DisparoBomba.RADIO &&
                            Math.abs(nave.y - disparoBomba.y) <= DisparoBomba.RADIO) {
                        aBorrar = colisionDisparoNave(disparoBomba);
                        Log.d("EXPLOTANDO", "BOMBA EXPLOTA");
                        break;
                    }
                }
            }
        }
        if (!nave.contrataque) {
            if (!(aBorrar instanceof DisparoEnemigoLanzallamas) && !(aBorrar instanceof DisparoVista))
                disparosEnemigos.remove(aBorrar);
        } else {
            disparosEnemigos.remove(aBorrar);
        }

        DisparoHelicoptero disparoHelicopteroBorrar = null;
        for (DisparoHelicoptero disparoHelicoptero : disparosHelicopteros) {
            if (disparoHelicoptero.colisiona(nave)) {
                nave.setVida(nave.getVida() - 1);
                nave.activarInvunerabilidad();
                Runnable action = new Runnable() {
                    @Override
                    public void run() {
                        nave.desactivarInvunerabilidad();
                    }
                };
                new Hilo(2000, action).start();
                disparoHelicopteroBorrar = disparoHelicoptero;
            }
        }

        if (disparoHelicopteroBorrar != null)
            disparosHelicopteros.remove(disparoHelicopteroBorrar);
    }

    private DisparoEnemigo colisionDisparoNave(DisparoEnemigo d) {
        DisparoEnemigo aBorrar;
        nave.setVida(nave.getVida() - d.getDamage());
        nave.activarInvunerabilidad();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                nave.desactivarInvunerabilidad();
            }
        };
        new Hilo(2000, action).start();

        if (d instanceof DisparoEnemigoRalentizador) {
            nave.detenerNave();

            Runnable action2 = new Runnable() {
                @Override
                public void run() {
                    nave.recuperarVelocidad();
                }
            };
            new Hilo(1000, action2).start();
        }
        aBorrar = d;
        return aBorrar;
    }

    private void colisionaEnemigos() {
        Enemigo eliminar = null;
        for (Enemigo e : enemigos) {
            if (e.colisiona(nave)) {
                if (!nave.esInvulnerable()) {
                    if (!nave.contrataque) {
                        if (coloresCajas.size() > 0) {
                            int colorCaja = coloresCajas.get(coloresCajas.size() - 1);
                            int colorEnemigo = e.getColor();

                            if (colorEnemigo == colorCaja && enemigoColorCaja == null) {
                                nave.sumarPuntos(1);
                                enemigoColorCaja = e;
                            }
                        } else {
                            nave.setVida(nave.getVida() - 1);
                            nave.activarInvunerabilidad();
                            Runnable action = new Runnable() {
                                @Override
                                public void run() {
                                    nave.desactivarInvunerabilidad();
                                }
                            };
                            new Hilo(5000, action).start();
                        }
                    } else {
                        eliminar = e;

                        Iterator<DisparoEnemigo> disparoEnemigoIterator = disparosEnemigos.iterator();
                        while (disparoEnemigoIterator.hasNext()) {
                            DisparoEnemigo disparoEnemigo = disparoEnemigoIterator.next();

                            if (disparoEnemigo.enemigo == e) {
                                disparoEnemigoIterator.remove();
                            }
                        }
                    }
                } else {
                    if (e == enemigoColorCaja)
                        enemigoColorCaja = null;
                }
            }
        }
        if (eliminar != null)
            enemigos.remove(eliminar);
    }

    private void colisionesPowerUps() {
        PowerUp eliminar = null;
        for (PowerUp p : powerups) {
            if (p.colisiona(nave)) {
                p.efecto(this);
                eliminar = p;
            }
        }
        powerups.remove(eliminar);
    }

    public void comprobarDisparos() {
        long tiempo = System.currentTimeMillis();
        for (Enemigo e : enemigos) {
            if (e instanceof Disparador) {
                Disparador disparador = (Disparador) e;
                final DisparoEnemigo disparo = disparador.disparar(tiempo);
                if (disparo != null)
                    disparosEnemigos.add(disparo);
                if (e instanceof EnemigoVista) {
                    EnemigoVista vista = (EnemigoVista) e;
                    int actual = disparosEnemigos.indexOf(vista.actual);
                    vista.cambiarLadoDisparo();
                    disparosEnemigos.remove(actual);
                    disparosEnemigos.add(vista.actual);
                }
                if (disparo instanceof DisparoBomba) {
                    Runnable action = new Runnable() {
                        @Override
                        public void run() {
                            DisparoBomba disp = ((DisparoBomba) disparo);
                            disp.explotando = true;
                            disp.imagen = CargadorGraficos.cargarDrawable(context, R.drawable.explosion_bomba);
                            disp.altura = disp.altura + 5;
                            disp.ancho = disp.ancho + 10;
                        }
                    };

                    Runnable timerBomba = new Runnable() {
                        @Override
                        public void run() {
                            disparosEnemigos.remove(disparo);
                        }
                    };

                    new Hilo(2000, action).start();

                    new Hilo(4000, timerBomba).start();
                }
            }

            for (Helicoptero helicoptero : helicopteros) {
                DisparoHelicoptero disparoHelicoptero = helicoptero.disparar(tiempo);
                if (disparoHelicoptero != null)
                    disparosHelicopteros.add(disparoHelicoptero);
            }
        }

    }

    private void explotar(DisparoEnemigo disparo) {
        if (disparo.colisiona(nave)) {
            nave.setVida(nave.getVida() - 1);
            nave.activarInvunerabilidad();
            Runnable action = new Runnable() {
                @Override
                public void run() {
                    nave.desactivarInvunerabilidad();
                }
            };
            new Hilo(2000, action).start();
        }
    }


    private void moverEnemigos() {
        for (Enemigo enemigo : enemigos) {
            enemigo.mover();
            if (enemigo instanceof EnemigoVista) {
                return;
            }
            int tileXDerecha = (int) ((enemigo.x + enemigo.ancho / 2) / Tile.ancho);
            int tileXIzquierda = (int) ((enemigo.x - enemigo.ancho / 2) / Tile.ancho);

            if (tileXDerecha < anchoMapaTiles()) {
                int aux = (int) (enemigo.y / Tile.altura);
                if(aux >= altoMapaTiles())
                    aux = altoMapaTiles()-1;
                if (mapaTiles[tileXDerecha]
                        [aux].tipoDeColision
                        != Tile.PASABLE) {
                    enemigo.x = enemigo.xAnterior;
                    enemigo.girar();
                }
            }

            if (tileXIzquierda >= 0) {
                int auxY = (enemigo.y / Tile.altura >=
                        altoMapaTiles()) ? altoMapaTiles()-1 : (int)enemigo.y / Tile.altura;
                if (mapaTiles[tileXIzquierda]
                        [auxY].tipoDeColision
                        != Tile.PASABLE) {
                    enemigo.x = enemigo.xAnterior;
                    enemigo.girar();
                }
            }
        }
    }

    private void moverDisparos() {
        List<DisparoEnemigo> aBorrar = new ArrayList<>();

        for (DisparoEnemigo d : disparosEnemigos) {
            d.moverAutomaticamente();
            int tileXDerecha = (int) ((d.x + d.ancho / 2) / Tile.ancho);
            int tileXIzquierda = (int) ((d.x - d.ancho / 2) / Tile.ancho);

            if (d.orientacion) {

                if (tileXDerecha < anchoMapaTiles()) {
                    if (mapaTiles[tileXDerecha]
                            [(int) (d.y / Tile.altura)].tipoDeColision
                            != Tile.PASABLE) {
                        aBorrar.add(d);
                    }
                }
            } else {
                if (tileXIzquierda >= 0) {
                    if (mapaTiles[tileXIzquierda]
                            [(int) (d.y / Tile.altura)].tipoDeColision
                            != Tile.PASABLE) {
                        aBorrar.add(d);
                    }
                }
            }

            if (d.x > GameView.pantallaAncho || d.x < 0)
                aBorrar.add(d);
        }

        Iterator<DisparoEnemigo> disparoEnemigoIterator = disparosEnemigos.iterator();
        while (disparoEnemigoIterator.hasNext()) {
            DisparoEnemigo d = disparoEnemigoIterator.next();
            if (!(d instanceof DisparoEnemigoLanzallamas) && !(d instanceof DisparoVista))
                disparoEnemigoIterator.remove();
        }
    }

    private void moverNaveHorizontal(int tileXnaveIzquierda, int tileXnaveDerecha,
                                     int tileYnaveInferior, int tileYnaveCentro, int tileYnaveSuperior) {
        if (nave.velocidadXActual > 0) {
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
                if (nave.velocidadXActual > 0)
                    nave.x += nave.velocidadXActual;

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
                    double velocidadNecesaria = Math.min(distanciaX, nave.velocidadXActual);
                    nave.x += velocidadNecesaria;
                } else {
                    // Opcional, corregir posición
                    nave.x = TilenaveBordeDerecho - nave.ancho / 2;
                }
            }
        }

        // izquierda
        if (nave.velocidadXActual < 0) {
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

                if (nave.velocidadXActual < 0)
                    nave.x += nave.velocidadXActual;

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
                    double velocidadNecesaria = Utilidades.proximoACero(-distanciaX, nave.velocidadXActual);
                    nave.x += velocidadNecesaria;
                } else {
                    // Opcional, corregir posición
                    nave.x = TilenaveBordeIzquierdo + nave.ancho / 2;
                }
            }
        }
    }

    private void moverNaveVertical(int tileXnaveIzquierda, int tileXnaveDerecha,
                                   int tileYnaveInferior, int tileYnaveCentro, int tileYnaveSuperior) {
        if (nave.velocidadYActual > 0) {
            // Tengo un tile delante y es PASABLE
            // El tile de delante está dentro del Nivel
            if (tileYnaveInferior + 1 <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXnaveDerecha][tileYnaveInferior + 1].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveInferior + 1].tipoDeColision ==
                            Tile.PASABLE) {
                if (nave.velocidadYActual > 0)
                    nave.y += nave.velocidadYActual;

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
                int TilenaveBordeInferior = tileYnaveInferior * Tile.altura + Tile.altura;
                double distanciaY = TilenaveBordeInferior - (nave.y + nave.altura / 2);

                if (distanciaY > 0) {
                    double velocidadNecesaria = Math.min(distanciaY, nave.velocidadYActual);
                    nave.y += velocidadNecesaria;
                } else {
                    // Opcional, corregir posición
                    nave.y = TilenaveBordeInferior - nave.altura / 2;
                }
            }
        }
        // arriba
        if (nave.velocidadYActual < 0) {

            if (tileYnaveSuperior - 1 >= 0 &&
                    mapaTiles[tileXnaveDerecha][tileYnaveSuperior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXnaveIzquierda][tileYnaveSuperior].tipoDeColision ==
                            Tile.PASABLE) {

                if (nave.velocidadYActual < 0)
                    nave.y += nave.velocidadYActual;

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
                int TilenaveBordeSuperior = tileYnaveSuperior * Tile.altura;
                double distanciaY = (nave.y - nave.altura / 2) - TilenaveBordeSuperior;

                if (distanciaY > 0) {
                    double velocidadNecesaria = Utilidades.proximoACero(-distanciaY, nave.velocidadYActual);
                    nave.y += velocidadNecesaria;
                } else {
                    // Opcional, corregir posición
                    //nave.y = TilenaveBordeSuperior + nave.altura / 2;
                }
            }
        }
    }


    public void actualizar(long tiempo) {
        if (inicializado) {
            nave.procesarOrdenes(orientacionPadX, orientacionPadY);
            nave.actualizar(tiempo);
            this.aplicarReglasMovimiento();
            this.comprobarDisparos();
            for (Enemigo e : this.enemigos)
                e.actualizar(tiempo);
            marcadorPuntos.puntos = nave.getPuntos();
            for (PowerUp p : powerups)
                if (p instanceof MonedaRecolectable)
                    p.actualizar(tiempo);
            for (Helicoptero helicoptero : helicopteros)
                helicoptero.actualizar(tiempo);
            for (DisparoHelicoptero disparoHelicoptero : disparosHelicopteros)
                disparoHelicoptero.actualizar(tiempo);
            for (DisparoEnemigo disparoEnemigo : disparosEnemigos) {
                disparoEnemigo.actualizar(tiempo);
            }


            comprobarVictoriaDerrota();

            comprobarMaxTiempoQuieta();

            this.eliminarModelosEnemigos();
        }
    }

    private void eliminarModelosEnemigos(){
        this.eliminarModelosFueraMapa(enemigos);
        this.eliminarModelosFueraMapa(helicopteros);
        this.eliminarModelosFueraMapa(disparosEnemigos);
        this.eliminarModelosFueraMapa(disparosHelicopteros);
        this.eliminarModelosFueraMapa(powerups);
    }

    private <T extends Modelo> void eliminarModelosFueraMapa(List<T> modelos){
        Iterator<T> iterator = modelos.iterator();
        while(iterator.hasNext()){
            if(iterator.next().y >= altoMapaTiles() * Tile.altura)
                iterator.remove();
        }
    }

    public void dibujar(Canvas canvas) {
        if (inicializado) {
            fondo.dibujar(canvas);
            dibujarTiles(canvas);
            nave.dibujar(canvas);

            for (DisparoEnemigo d : disparosEnemigos)
                d.dibujar(canvas);

            for (Enemigo e : enemigos)
                e.dibujar(canvas);

            for (PowerUp p : powerups)
                p.dibujar(canvas);

            for (Helicoptero helicoptero : helicopteros)
                helicoptero.dibujar(canvas);
            for (DisparoHelicoptero disparoHelicoptero : disparosHelicopteros)
                disparoHelicoptero.dibujar(canvas);
            marcadorPuntos.dibujar(canvas);
        }
    }

    private void dibujarTiles(Canvas canvas) {
        // Calcular que tiles serán visibles en la pantalla
        // La matriz de tiles es más grande que la pantalla

        int tileYNave = (int) nave.y / Tile.altura;
        int izquierda = 0; //El primer tile

        int derecha = izquierda +
                (GameView.pantallaAncho / Tile.ancho) + 1;

        aplicarScroll();

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

    private void generarPowerUpsAleatorioArriba(){
        int conta = 0;
        for (int y = 0; y < altoMapaTiles()/2; y++) {
            if(conta < 5) {
                int x;
                do {
                    x = Utils.randBetween(0, anchoMapaTiles() - 2);
                }
                while (mapaTiles[x][y].tipoDeColision == Tile.SOLIDO);
                generarPowerUp(Utils.randBetween(0, 9), x, y);
                conta++;
            }
        }
    }

    private int numTilesLibresFila(int y){
        int num = 0;
        for(int x = 0;x < anchoMapaTiles();x++)
            if(mapaTiles[x][y].tipoDeColision == Tile.SOLIDO)
                num++;
        return num;
    }

    private int numFilasConTile(){
        int num = 0;
        for(int y=0;y < altoMapaTiles()/2;y++)
            for(int x=0;x < anchoMapaTiles();x++)
                if(mapaTiles[x][y].tipoDeColision == Tile.SOLIDO)
                    num++;
        return num;
    }

    private void generarPowerUpsAleatorioAbajo(){
        int conta = 0;
        for (int y = altoMapaTiles()/2; y < altoMapaTiles(); y++) {
            if(conta < 5) {
                int x;
                do {
                    x = Utils.randBetween(0, anchoMapaTiles() - 2);
                }
                while (mapaTiles[x][y].tipoDeColision == Tile.SOLIDO);
                generarPowerUp(Utils.randBetween(0, 9), x, y);
                conta++;
            }
        }
    }

    private Tile generarPowerUp(int tipo,int x,int y){
        //M,H,S,I,X,C,T,P,R,F,G
        switch (tipo){
            case 0:
                return this.inicializarTile('M', x, y);
            case 1:
                return this.inicializarTile('H', x, y);
            case 2:
                return this.inicializarTile('S', x, y);
            case 3:
                return this.inicializarTile('I', x, y);
            case 4:
                return this.inicializarTile('X', x, y);
            case 5:
                return this.inicializarTile('C', x, y);
            case 6:
                return this.inicializarTile('T', x, y);
            case 7:
                return this.inicializarTile('P', x, y);
            case 8:
                return this.inicializarTile('R', x, y);
            case 9:
                return this.inicializarTile('F', x, y);
            default:
                return this.inicializarTile('.', x, y);
        }
    }

    private void generarEnemigosAleatorios(int fromY, int toY) {
        int conta = 0;
        for (int y = fromY; y < toY; y++) {
            int x = -1;
            if(!this.comprobarFilaSinTiles(y) && this.numTilesLibresFila(y) >= 4 ) {
                do {
                    x = Utils.randBetween(0, anchoMapaTiles() - 1);
                }
                while(mapaTiles[x][y].tipoDeColision == Tile.SOLIDO);
            }
            else
            if(this.comprobarFilaSinTiles(y)){
                x = Utils.randBetween(0, anchoMapaTiles() - 2);
                // generarEnemigo(Utils.randBetween(0, 7), x, y);
            }

            if(x != -1 && conta < numEnemigosActual){
                generarEnemigo(Utils.randBetween(0, 7), x, y);
                conta++;
            }
        }
    }
    private void generarEnemigosAleatoriosArriba() {
       generarEnemigosAleatorios(0, altoMapaTiles() / 2);
    }

    private void generarEnemigosAleatoriosAbajo(){
        generarEnemigosAleatorios(altoMapaTiles() / 2, altoMapaTiles());
    }

    private Tile generarEnemigo(int i, int x, int y) {
        i = 3;

        switch (i) {
            case 0:
                return this.inicializarTile('B', x, y);
            case 1:
                return this.inicializarTile('L', x, y);
            case 2:
                return this.inicializarTile('Z', x, y);
            case 3:
                return this.inicializarTile('O', x, y);
            case 4:
                return this.inicializarTile('K', x, y);
            default:
                return this.inicializarTile('.', x, y);

        }
    }

    private boolean comprobarFilaSinTiles(int y) {
        for (int x = 0; x < anchoMapaTiles(); ++x)
            if (mapaTiles[x][y].tipoDeColision == Tile.SOLIDO)
                return false;
        return true;
    }

    public int anchoMapaTiles() {
        return mapaTiles.length;
    }

    public int altoMapaTiles() {

        return mapaTiles[0].length;
    }

    private void inicializarMapaTiles() throws Exception {
        if (infinito) {
            inicializarMapaTilesInfinito();
            return;
        }
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

    private void inicializarMapaTilesInfinito() {
        int anchoNivel = 8;
        int altoNivel = 20;
        mapaTiles = new Tile[anchoNivel][altoNivel * 2];
        this.inicializarMapaTilesAleatorioArriba();
        this.inicializarMapaTilesAleatorioAbajo();
    }

    private void inicializarMapaTilesAleatorioArriba() {
        int numeroFilasTile = Utils.randBetween(4, 6);
        int[] posicionesFilaConTile = this.getFilasConTile(numeroFilasTile, true);

        for (int y = 0; y < altoMapaTiles() / 2; ++y) {
            if (yaExisteFila(posicionesFilaConTile, y))
                this.generarTilesEnEjeX(y);
            else
                for (int x = 0; x < anchoMapaTiles(); ++x)
                    mapaTiles[x][y] = inicializarTile('.', x, y);
        }

        this.generarEnemigosAleatoriosArriba();
        this.generarPowerUpsAleatorioArriba();
    }

    private void generarTilesEnEjeX(int y) {
        int posicionInicio = Utils.randBetween(3, anchoMapaTiles() - 3);
        int numTiles = Utils.randBetween(2, anchoMapaTiles() - 4);
        int orientacion = Utils.randBetween(0, 1);
        int limite;
        if (orientacion == 0) {
            if (posicionInicio + numTiles >= anchoMapaTiles()) {
                for (int x = 0; x < anchoMapaTiles(); x++)
                    if (x >= posicionInicio)
                        mapaTiles[x][y] = inicializarTile('#', x, y);
                    else
                        mapaTiles[x][y] = inicializarTile('.', x, y);
            } else {
                for (int x = posicionInicio; x < posicionInicio + numTiles; x++)
                    mapaTiles[x][y] = inicializarTile('#', x, y);
                for (int x = 0; x < anchoMapaTiles(); x++)
                    if (x < posicionInicio || x >= posicionInicio + numTiles)
                        mapaTiles[x][y] = inicializarTile('.', x, y);
            }
        } else {
            if (posicionInicio - numTiles < 0) {
                for (int x = posicionInicio - 1; x >= 0; x--)
                    mapaTiles[x][y] = inicializarTile('#', x, y);
                for (int x = posicionInicio; x < anchoMapaTiles(); x++)
                    mapaTiles[x][y] = inicializarTile('.', x, y);
            } else {
                for (int x = posicionInicio; x > posicionInicio - numTiles; x--)
                    mapaTiles[x][y] = inicializarTile('#', x, y);
                for (int x = 0; x < anchoMapaTiles(); x++)
                    if (x <= posicionInicio - numTiles || x > posicionInicio)
                        mapaTiles[x][y] = inicializarTile('.', x, y);
            }
        }
    }

    private void inicializarMapaTilesAleatorioAbajo() {
        int numeroFilasTile = Utils.randBetween(5, 8);
        int[] posicionesFilaConTile = this.getFilasConTile(numeroFilasTile, false);
        for (int y = altoMapaTiles() / 2; y < altoMapaTiles(); ++y) {
            if (yaExisteFila(posicionesFilaConTile, y)) {
                this.generarTilesEnEjeX(y);
            } else
                for (int x = 0; x < anchoMapaTiles(); ++x)
                    mapaTiles[x][y] = inicializarTile('.', x, y);
        }
        mapaTiles[3][altoMapaTiles() - 1] =
                inicializarTile('1', 3, altoMapaTiles() - 1);
        this.generarEnemigosAleatoriosAbajo();
        this.generarPowerUpsAleatorioAbajo();
    }


    private int[] getFilasConTile(int numFilas, boolean arriba) {
        int min, max;
        if (arriba) {
            min = 10;
            max = 19;
        } else {
            min = 0;
            max = 9;
        }
        int[] posicionFilas = new int[numFilas];
        inicializarPosiciones(posicionFilas);
        for (int i = 0; i < numFilas; i++) {
            int pos = -1;
            do{
                 pos = Utils.randBetween(min, max);
            }
            while(yaExisteFila(posicionFilas,pos));
            posicionFilas[i] = pos;
        }
        Arrays.sort(posicionFilas);
        for(int i=0;i < posicionFilas.length-1;i++){
            if(posicionFilas[i+1] == posicionFilas[i]+1){
                posicionFilas[i+1] += 1;
            }
        }
        return posicionFilas;
    }

    private void inicializarPosiciones(int[] posiciones) {
        for (int i = 0; i < posiciones.length; i++)
            posiciones[i] = -1;
    }

    private boolean yaExisteFila(int[] posiciones, int pos) {
        for (int i = 0; i < posiciones.length; i++)
            if (posiciones[i] == pos)
                return true;
        return false;
    }

    private char generarTileAleatoriamente() {
        return '.';
    }

    private Tile inicializarTile(char codigoTile, int x, int y) {
        int xCentroAbajoTile = x * Tile.ancho + Tile.ancho / 2;
        int yCentroAbajoTile = y * Tile.altura + Tile.altura;
        switch (codigoTile) {
            case '1':
                nave = new Nave(context, xCentroAbajoTile, yCentroAbajoTile);
                scrollEjeY = (int) altoMapaTiles() * Tile.altura - GameView.pantallaAlto;
                return new Tile(null, Tile.PASABLE);
            case 'M':
                powerups.add(new MonedaRecolectable(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case '.':
                // en blanco, sin textura
                return new Tile(null, Tile.PASABLE);
            case '#':
                // bloque de musgo, no se puede pasar
                return new Tile(CargadorGraficos.cargarDrawable(context,
                        R.drawable.blocka2), Tile.SOLIDO);
            case 'B':
                this.enemigos.add(new EnemigoDisparador(
                        context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'L':
                this.enemigos.add(new EnemigoLanzallamas(
                        context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'Z':
                this.enemigos.add(new EnemigoRalentizador(
                        context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'O':
                this.enemigos.add(new EnemigoLanzaBombas(
                        context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'V':
                this.enemigos.add(new EnemigoVista(
                        context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'K':
                this.helicopteros.add(new Helicoptero(
                        context, xCentroAbajoTile, yCentroAbajoTile
                ));
                return new Tile(null, Tile.PASABLE);
            case 'H':
                powerups.add(new CajaVidaExtra(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'S':
                powerups.add(new CajaVelocidad(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'I':
                powerups.add(new CajaInvulnerabilidad(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'X':
                powerups.add(new CajaBomba(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'C':
                powerups.add(new CajaColor(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'T':
                powerups.add(new Teletransporte(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'P':
                powerups.add(new CajaPuntosExtra(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'R':
                powerups.add(new CajaAleatoria(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'F':
                powerups.add(new CajaContraEnemigos(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'G':
                powerups.add(new CajaEnemigos(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'N':
                powerups.add(new CajaLentitud(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'D':
                powerups.add(new CajaDestruccion(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            case 'E':
                powerups.add(new CajaSemiInvulnerabilidad(context, xCentroAbajoTile, yCentroAbajoTile));
                return new Tile(null, Tile.PASABLE);
            default:
                //cualquier otro caso
                return new Tile(null, Tile.PASABLE);
        }
    }

    private float tilesEnDistanciaY(double distanciaY) {
        return (float) distanciaY / Tile.altura;
    }

    public int getTile(int x, int y) {
        return this.mapaTiles[x][y].tipoDeColision;
    }

    public void aplicarScroll() {
        if (nave.y < altoMapaTiles() * Tile.altura - GameView.pantallaAlto * 0.3)
            if (nave.y + scrollEjeY > GameView.pantallaAlto * 0.7) {
                scrollEjeY = (int) ((nave.y) - GameView.pantallaAlto * 0.7);
            }

        if (nave.y > GameView.pantallaAlto * 0.3)
            if (nave.y + scrollEjeY < GameView.pantallaAlto * 0.3) {
                scrollEjeY = (int) (nave.y - GameView.pantallaAlto * 0.3);
            }

        double naveY;
        if (infinito) {
            naveY = nave.y - (18 * Tile.altura * (numMapa - 1));
            Log.println(Log.DEBUG,"NAVEY:",""+naveY);
        } else {
            naveY = nave.y;
        }

        if (naveY < minPosNaveY) {
            nave.sumarPuntos((int) ((minPosNaveY - naveY) / 4));
            minPosNaveY = naveY;
        }
    }

    private void comprobarMaxTiempoQuieta() {
        if (ultimaPosYNave > -1) {
            if (nave.y != ultimaPosYNave) {
                ultimaPosYNave = nave.y;
                tiempoUltimoMovimientoNave = System.currentTimeMillis();
            }
        } else {
            if (nave.y != nave.yInicial) {
                ultimaPosYNave = nave.y;
                tiempoUltimoMovimientoNave = System.currentTimeMillis();
            }
        }

        if (tiempoUltimoMovimientoNave > -1) {
            if (System.currentTimeMillis() - tiempoUltimoMovimientoNave >= (dificultad.getMaxSegundosQuieto() * 1000)) {
                nave.vida = 0;
            }
        }
    }

    private void comprobarVictoriaDerrota() {
        if (nave.vida <= 0) {
            if (!infinito) {
                GameView.gameloop.setRunning(false);
                context.startActivity(new Intent(context, DerrotaActivity.class));
            } else {
                GameView.gameloop.setRunning(false);
                Utils.mostrarPuntos(context, marcadorPuntos.puntos);
            }
        }

        if (!infinito) {
            int tileYNave = (int) (nave.y / Tile.altura);

            if (tileYNave <= 0) {
                GameView.gameloop.setRunning(false);
                context.startActivity(new Intent(context, VictoriaActivity.class));
            }
        }
    }
}
