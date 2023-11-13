
public class DoubleLinkedListOfHeaders {
    private Node header;
    private Node trailer;
    private int count;
    
    private class Node {
        public String element; 
        NodePalavra PalavrasComMesmaInicial; // Lista com as demais palavras da letra
        public Node next;
        public Node prev;

        public Node(String e) {
            this.element = e;
            this.next = null;
            prev = null;
            this.PalavrasComMesmaInicial = null;
        }
    }
    
    private class NodePalavra {
        public String element;
        public NodePalavra next;

        public NodePalavra(String e) {
            element = e;
            next = null;
        }
    }

    public DoubleLinkedListOfHeaders() {
        header = new Node(null);
        trailer = new Node(null);
        header.next =  trailer;
        trailer.prev = header;
        count = 0;
    }
    
    //@return true se a lista não contem elementos
    public boolean isEmpty() {
        return (count == 0);
    }

    String geraHash()
    {
        HashCreator hash = new HashCreator();
        
        String s = hash.generateMD5(toString());    
        return s;
    }

    //Não modificar esse método!
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        Node aux = header.next;
        for (int i = 0; i < count; i++) {
            s.append("**  " + aux.element.toString());
      
            NodePalavra aux2 = aux.PalavrasComMesmaInicial;
            if (aux2 != null)
                s.append(", ");
            else s.append("\n");
            while(aux2 != null)
            {
                s.append(aux2.element.toString());
                if (aux2.next != null)
                    s.append(", ");
                else s.append("\n");
                aux2 = aux2.next;
            }
            aux = aux.next;
        }
        return s.toString();
    }    
    void Imprime(String s)
    {
        System.out.print(s);
    }
    void ImprimeLista()
    {
        Imprime("\n**********************\n");
        Imprime("A Lista:\n");
        Imprime(toString());
        Imprime("HASH: " + geraHash()+ "\n");
    }

    void addPalavrasNaSublista(Node n, String newWord) {   
        char pletra = newWord.charAt(0); 
        Node current = header.next;    
        for (int i = 0; i<count; i++) {
        
        //começando pela header checa se as iniciais são iguais E se a palavra vem antes ou depois do 
        //nodo CURRENT 
        if(current.element.compareTo(newWord) > 0 && current.element.charAt(0) == pletra){
            //caso exista uma palavra (CURRENT) que venha depois da nova (NEWWORD), ela é adicionada na sublista --passada
            //para trás-- e a nova palavra removida para que se evitem duplicatas
            addNodoNaSublista(current, current.element);
            Remove(newWord);

            String aux = current.element;
            current.element = newWord;
            newWord = aux;
        } 
        else if (current.element.compareTo(newWord) < 0 && current.element.charAt(0) == pletra) {
            //caso exista uma palavra que vem antes da nova palavra ela é removida da lista principal e 
            //adicionada logo após a ultima palavra encontrada (CURRENT)
            Remove(newWord);
            addNodoNaSublista(current, newWord);
        }
        current = current.next;
        }
    }
    
    //esse método adiciona o nodo principal(DoubleLinkedList) em um NodoPalavra que se encaixe nas 
    //propriedades da sublista
    void addNodoNaSublista(Node n, String newWord) {
        //cria a sublista que segue a palavra inicial da lista principal
        NodePalavra subList = new NodePalavra(newWord);
    
        if (n.PalavrasComMesmaInicial == null) {
            n.PalavrasComMesmaInicial = subList;
        } 
        //verifica se vem depois de alguma palavra ja existente
        else if (newWord.compareTo(n.PalavrasComMesmaInicial.element) < 0) {
            subList.next = n.PalavrasComMesmaInicial;
            n.PalavrasComMesmaInicial = subList;
        } 
        //verifica se vem antes de alguma palavra ja existente
        else {
            NodePalavra current = n.PalavrasComMesmaInicial;
            while (current.next != null && newWord.compareTo(current.next.element) > 0) {
                current = current.next;
            }
            subList.next = current.next;
            current.next = subList;
        }
    }

    //Metodo principal - Adiciona na lista principal
    public void addIncreasingOrder(String palavra){
        int i;
        Node aux = header.next; 

        for(i=0; i<count; i++) {
            //caso ache um elemento (aux) em que a inicial venha depois da PALAVRA 
            if (aux.element.compareTo(palavra) > 0)
                break;
            aux = aux.next;
        }
        //cria um novo nodo e o adiciona a Lista ordenada - a checagem da sublista é feita separadamente
        Node n = new Node(palavra);
        n.next = aux;
        n.prev = aux.prev;
        
        //atualiza suas ligações e o contador
        aux.prev.next = n;
        aux.prev = n;
        count++;           

        //adiciona o nodo na sublista para assim ordena-lo corretamente
        addPalavrasNaSublista(n, palavra);
    }

    void Remove(String palavra) {
        Node aux = header.next;
        //o boolean checa se a palavra existe na lista principal para que assim haja uma verificação 
        //da sublista
        boolean listaPrincipal = false;
    
        while (aux != trailer) {
            if (aux.element.equals(palavra)) {
                //apenas uma verificação de duas etapas para as listas REM
                //Para evitar que sublista suma junto com a eliminação do nodo principal
                if (palavra.charAt(0) == '-') {
                    NodePalavra palavraAux = aux.PalavrasComMesmaInicial;
                    while (palavraAux != null) {
                        NodePalavra next = palavraAux.next;
                        addIncreasingOrder(palavraAux.element);
                        palavraAux = next;
                    }
                    //Remove o nodo da lista principal da lista principal
                    aux.prev.next = aux.next;
                    aux.next.prev = aux.prev;
                    listaPrincipal = true;
                    count--;
    
                    break;
                } 
                else {
                    // Remova o nodo da lista principal
                    aux.prev.next = aux.next;
                    aux.next.prev = aux.prev;
                    listaPrincipal = true;
                    count--;
    
                    //Para evitar que sublista suma junto com a eliminação do nodo principal
                    NodePalavra palavraAux = aux.PalavrasComMesmaInicial;
                    while (palavraAux != null) {
                        NodePalavra next = palavraAux.next;
                        addIncreasingOrder(palavraAux.element);
                        listaPrincipal = true;
                        palavraAux = next;
                    }
                    break;
                }
            }
            aux = aux.next;
        }
    
        //caso a palavra não for encontrada na lista principal a verificação é levada para a sublista
        if (!listaPrincipal) {
            RemoveSublista(palavra);
        }
    }

    void RemoveSublista(String palavra) {
        Node current = header.next;
    
        while (current != trailer) {
            if (current.PalavrasComMesmaInicial != null) {
                NodePalavra palavraAux = current.PalavrasComMesmaInicial;
                NodePalavra prev = palavraAux;
                
                //procura uma palavra igual á PALAVRA prestes a ser removida
                while (palavraAux != null && !palavraAux.element.equals(palavra)) {
                    prev = palavraAux;
                    palavraAux = palavraAux.next;
                }

                if (palavraAux != null) {
                    if (palavraAux == current.PalavrasComMesmaInicial) {
                        current.PalavrasComMesmaInicial = palavraAux.next;
                    } 
                    //caso a palavra for achada e passar pela verificação de não null(fim da lista) é feita
                    //a remoção do nodo
                    else {
                        prev.next = palavraAux.next;
                    }
                }
            }
            current = current.next;
        }
    }
    
    // ********************************************************************************************************************************
    // Deste ponto em diante, o código serve para gerar codigo DOT
    // NAO ALTERE

    // Gera uma saida no formato DOT
    // Esta saida pode ser visualizada no GraphViz
    // Versoes online do GraphViz pode ser encontradas em
    // http://www.webgraphviz.com/
    public void GeraDOT() 
    {
        Imprime("digraph g { \nnode [shape = record,height=.1];\n" + "\n");

        Imprime("rankdir=LR;\n");
        GeraNodosDOT();
        GeraConexoesDOT();
        
        Imprime("}" + "\n");
    }
    public void GeraNodosDOT()
    {
        String S1, S2;
        S1 = "{rank = same;";
        S2 = "{rank = same;";

        Node aux = header.next;
        Imprime("node" + "HEADER" + "[label= \" " + "HEADER" + " | <next> \"]" + "\n");

        for(int i=0; i<count; i++) 
        {
            //Imprime("node" + aux.element + "[label= \" " + aux.element + " | <next> \"]" + "\n");
            Imprime("node" + aux.element + "[label= \" " + aux.element + " | <pal> | <next>\"]" + "\n");
            NodePalavra aux2 = aux.PalavrasComMesmaInicial;
            while(aux2 != null)
            {
                Imprime("node" + aux2.element + "[label= \" " + aux2.element + " | <next>\"]" + "\n");            
                aux2 = aux2.next;
            }
            S1 = S1 + "node" + aux.element + ";";
            aux = aux.next;
        } 
        Imprime (S1+"}\n");
    }

    public void GeraConexoesDOT()
    {
        Node aux = header.next;
        Imprime("\"node" + "HEADER" + "\":next -> \"node" + aux.element + "\" " + "\n");

        // "node150":esq -> "node23"
        for(int i=0; i<count; i++) 
        {
            if (aux.next != trailer)
                Imprime("\"node" + aux.element + "\":next -> \"node" + aux.next.element + "\" " + "\n");
            NodePalavra aux2 = aux.PalavrasComMesmaInicial;
            if (aux.PalavrasComMesmaInicial != null)
            {
                Imprime("subgraph cluster" + i + " {\n");
                Imprime("\t");
                Imprime("\"node" + aux.element + "\":pal -> \"node" + aux2.element + "\" " + "\n");
                //aux2 = aux2.next;
                while(aux2 != null)
                {
                    if (aux2.next != null)
                        Imprime("\t\"node" + aux2.element + "\":next -> \"node" + aux2.next.element + "\" " + "\n");
                    aux2 = aux2.next;
                }
                Imprime("\tstyle= invis }\n");
            }
        
            aux = aux.next;
        }
        
    }
}