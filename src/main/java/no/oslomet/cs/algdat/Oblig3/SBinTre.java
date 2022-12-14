package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {

        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null; // p starter i roten
        int cmp = 0; // hjelpevariabel

        while (p != null) // fortsetter til p er ute av treet
        {
            q = p; // q er forelder til p
            cmp = comp.compare(verdi,p.verdi); // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre; // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi,q); // oppretter en ny node

        if (q == null) rot = p; // p blir rotnode
        else if (cmp < 0) q.venstre = p; // venstre barn til q
        else q.høyre = p; // høyre barn til q

        antall++; // én verdi mer i treet
        return true; // vellykket innlegging
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        Node<T> p = rot; //Initialiserer p
        int antallV = 0; //Hjelpevariabel for antall verdier.
        while (p != null) {
            int compr = comp.compare(verdi, p.verdi); //sammenligner verdier
            if (compr < 0) { //Oppdaterer venstrebarn
                p = p.venstre;
            } else {
                if (compr == 0) antallV++; //Funnet like verdier, og oppdaterer høyrebarn
                p = p.høyre;
            }
        }
        return antallV;
        }


    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Objects.requireNonNull(p); //Sjekker den første verdien av p i postorden.

        while (true)
        {
            if (p.venstre != null) p = p.venstre;
            else if (p.høyre != null) p = p.høyre;
            else return p; //her sjekker vi om p sin venster eller høyre ikke lik null, så returner vi p
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Node<T> forelder = p.forelder; //Initialiserer f som p.forelder.
        if (forelder == null) {
            return null; // hvis forelder er null, så retunrer i null
          }
        if (forelder.høyre == p || forelder.høyre == null){
            return forelder; //hvis forelder sin høyre barn lik p eller hvis forelder sin høyre barn lik null, så returnerer vi forelder
        }
        else {
            return førstePostorden(forelder.høyre);
        }
    }


    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = rot; //Initialiserer p som rot.

        Node<T> forste = førstePostorden(p); //Finner første node av metoden førstePostorden av p.

        oppgave.utførOppgave(forste.verdi);

        while (forste.forelder != null) {
            forste = nestePostorden(forste);
            oppgave.utførOppgave(Objects.requireNonNull(forste).verdi);
            //jeg har laget while løkke som looper gjennom treet og oppdaterer neste verdi i postorden.
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        if (!tom()) {
            postordenRecursive(rot, oppgave);
         }
        //Dersom treet ikke er tomt, så kaller vi rekursivt på metoden, start ved rot.
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == null) {
            return;
        }
            postordenRecursive(p.venstre, oppgave); //går gjennom treet for p sitt venstre barn og utføre oppgave...
            postordenRecursive(p.høyre, oppgave);  //går gjennom treet for p sitt høye barn og utføre oppgave...
            oppgave.utførOppgave(p.verdi); //utfør oppgaven for hvert steg...
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
