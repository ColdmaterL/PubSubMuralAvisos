package pubsub;

import java.io.Serializable;

public class Cell implements Serializable{

    private static final long serialVersionUID = 7526442295522776847L;

    private int contents = 0;
    private String nome="",mensagem="";

    public void set(int v,String NovoNome,String mensagemNovo){
        contents = v;
        nome=NovoNome;
        mensagem=mensagemNovo;
    }

    public int getNumero(){
        return contents;
    }
    public String getNome(){
        return nome;
    }
    public String getMensagem(){
        return mensagem;
    }
}