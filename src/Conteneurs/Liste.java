package Conteneurs;

public class Liste<T> {

    private T head;
    private Liste<T> suivant; 

    public Liste(T element) {
        this.head = element;
        this.suivant = null;
    }

    public void ajouter(T element) {
        if (this.suivant == null) {
            this.suivant = new Liste<>(element);
        } else {
            this.suivant.ajouter(element);
        }
    }

    public void afficher() {
        System.out.println(this.head);
        if (this.suivant != null) {
            this.suivant.afficher();
        }
    }
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index doit être positif");
        }
    
        Liste<T> current = this;
        int currentIndex = 0;
    
        while (current != null) {
            if (currentIndex == index) {
                return current.head;
            }
            current = current.suivant;
            currentIndex++;
        }
    
        throw new IndexOutOfBoundsException("Index hors des limites de la liste");
    }

    public void supprimer(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index doit être positif");
        }
    
        if (index == 0) {
            if (this.suivant != null) {
                this.head = this.suivant.head;
                this.suivant = this.suivant.suivant;
            } else {
                throw new IllegalStateException("Impossible de supprimer l'unique élément");
            }
            return;
        }
    
        Liste<T> current = this;
        int currentIndex = 0;
    
        while (current.suivant != null) {
            if (currentIndex == index - 1) {
                
                current.suivant = current.suivant.suivant;
                return;
            }
            current = current.suivant;
            currentIndex++;
        }
    
        throw new IndexOutOfBoundsException("Index hors des limites de la liste");
    }
}
