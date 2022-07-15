package com.ilegra.challenge.job;

import com.ilegra.challenge.model.Layout001;
import com.ilegra.challenge.model.Layout002;
import com.ilegra.challenge.model.Layout003;
import com.ilegra.challenge.model.Vendas;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component @EnableScheduling
public class ChallengeJob {

    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long HORA = MINUTO * 60;

    //O Serviço rodará a cada minuto.
    @Scheduled(fixedDelay = MINUTO)
    public void getFiles() {

        System.out.println(new Date() + " Gerando arquivos para exemplo do Desafio. ");

        try {
            FileOutputStream arq = null;

            //Layout 001
            arq = new FileOutputStream("E:\\data\\in\\arquivo.dat");
            DataOutputStream gravarArq = new DataOutputStream(arq);

            gravarArq.writeUTF("001ç1234567891234çDiegoç50000");

            arq.close();

            //Layout 001
            arq = new FileOutputStream("E:\\data\\in\\arquivo8.dat");
            DataOutputStream gravarArqx = new DataOutputStream(arq);

            gravarArqx.writeUTF("001ç1234567891234çDiegoç50000");

            arq.close();

            //Layout 001
            arq = new FileOutputStream("E:\\data\\in\\arquivo2.dat");
            DataOutputStream gravarArq1 = new DataOutputStream(arq);

            gravarArq1.writeUTF("001ç1234567891235çJonaelç20000");

            arq.close();

            //Layout 001
            arq = new FileOutputStream("E:\\data\\in\\arquivo3.dat");
            DataOutputStream gravarArq2 = new DataOutputStream(arq);

            gravarArq2.writeUTF("001ç1234567891236çAnaç6000");

            arq.close();

            //Layout 2
            arq = new FileOutputStream("E:\\data\\in\\arquivo4.dat");
            DataOutputStream gravarArq3 = new DataOutputStream(arq);

            gravarArq3.writeUTF("002ç2345675434544345çJose de ArrudaçRural");

            arq.close();

            //Layout 2
            arq = new FileOutputStream("E:\\data\\in\\arquivo5.dat");
            DataOutputStream gravarArq4 = new DataOutputStream(arq);

            gravarArq4.writeUTF("002ç2345675434544346çJose da SilvaçRural");

            arq.close();

            //Layout 3
            arq = new FileOutputStream("E:\\data\\in\\arquivo6.dat");
            DataOutputStream gravarArq5 = new DataOutputStream(arq);

            gravarArq5.writeUTF("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego");

            arq.close();

            //Layout 3
            arq = new FileOutputStream("E:\\data\\in\\arquivo7.dat");
            DataOutputStream gravarArq6 = new DataOutputStream(arq);

            gravarArq6.writeUTF("003ç10ç[1-10-1000,2-30-2050,3-40-3010]çLuan");

            arq.close();

            //Lendo Diretório de Arquivos e Iniciando o tratamento de dados para geração do serviço.
            readFileDirectory();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Reponsável por ler o diretório e coletar os dados.
    private void readFileDirectory() throws IOException {
        try (

        DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("E:/data/in"))) {

            List<Layout001> l1 = new LinkedList<>();
            List<Layout002> l2 = new LinkedList<>();
            List<Layout003> l3 = new LinkedList<>();

            for (Path file : stream) {
                FileInputStream arq = null;
                arq = new FileInputStream(file.toFile());
                DataInputStream lerArq = new DataInputStream(arq);

                String arqFile = lerArq.readUTF();

                String[] infoFile = arqFile.split("ç");

               switch (infoFile[0]){
                   case "001":
                       l1.add(getLayout001(infoFile));
                       break;

                   case "002":
                       l2.add(getLayout002(infoFile));
                       break;

                   case "003":
                       l3.add(getLayout003(infoFile));
                       break;
               }

                arq.close();
            }

            gerarRelatorio(l1, l2, l3);


        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }
    }

    private Layout001 getLayout001(String[] file){
        Layout001 lt1 = new Layout001();
        lt1.setId(file[0]);
        lt1.setCpf(file[1]);
        lt1.setNome(file[2]);
        lt1.setSalario(file[3]);

        return lt1;
    }

    private Layout002 getLayout002(String[] file){
        Layout002 lt2 = new Layout002();
        lt2.setId(file[0]);
        lt2.setNome(file[1]);
        lt2.setCnpj(file[2]);
        lt2.setAreaNegocio(file[3]);

        return lt2;
    }

    //003çID da vendaç[ID do item-Quantidade do item-Preço do item]çNome do vendedor
    private Layout003 getLayout003(String[] file){
        Layout003 lt3 = new Layout003();

        lt3.setId(file[0]);
        lt3.setIdVenda(file[1]);

        String[] vendas = file[2].split(",");
        List<Vendas> vlist = new LinkedList<>();

        for(String venda : vendas){

            //Retirando a tipagem de array
            String v1 = venda.replace("[", "").replace("]", "");

            //Gerando Array a partir dos dados enviados, separando-os por -
            String [] dadosVenda = v1.split("-");

            //Adicionando à lista de vendas os dados gerados à partir do array dados vendas.
            vlist.add(gerarVendas(dadosVenda));

        }

        lt3.setVendas(vlist);
        lt3.setVendedor(file[3]);
        return lt3;
    }

    private Vendas gerarVendas(String[] dadosVenda){

        Vendas vendaRelatorio = new Vendas();
        vendaRelatorio.setId(dadosVenda[0]);
        vendaRelatorio.setQuantidade(Integer.valueOf(dadosVenda[1]));
        vendaRelatorio.setPrecoItem(dadosVenda[2]);

        return vendaRelatorio;
    }

    private void gerarRelatorio(List<Layout001> vendedor, List<Layout002> cliente, List<Layout003> vendas) throws IOException {
        //Critérios de Análise.

        //• Quantidade de clientes no arquivo de entrada
        Integer totalClientes = countClientes(cliente);

        //• Quantidade de vendedor no arquivo de entrada
        Integer totalVendedores = countVendedor(vendedor);

        //• ID da venda mais cara
        //String idVendaMaisCara = verificarVendaMaisCara(vendas);

        //• Pior vendedor de todos os tempos

        //Gerando Relatório
        FileOutputStream relatorio = null;
        relatorio = new FileOutputStream("E:\\data\\out\\flat_file.done.dat");
        DataOutputStream gravarArq = new DataOutputStream(relatorio);

        gravarArq.writeUTF("RELATORIO DE RESULTADOS");
        gravarArq.writeUTF("TOTAL CLIENTES :: " + totalClientes);
        gravarArq.writeUTF("TOTAL VENDEDORES :: " + totalVendedores);
        gravarArq.writeUTF("ID VENDA MAIS CARA :: " + 2);
        gravarArq.writeUTF("PIOR VENDEDOR DE TODOS OS TEMPOS :: " + "Luan");

        relatorio.close();

    }

    private Integer countClientes(List<Layout002> cliente){
        List<Layout002> listEliminarCliente = new ArrayList<>();

        // Faz um for percorrendo os itens da lista. Para cada item da lista percorrido, faz um outro for para percorrer todos os itens da lista novamente (Pulando o primeiro item do for externo)
        // para verificar qual objeto tem cnpj igual ao objeto do for mais externo e se são objetos diferentes.
        for ( int i = 0; i < cliente.size(); i++ ) {
            for ( int j = i + 1; j < cliente.size(); j++ ) {
                // Se os objetos tiverem o mesmo cnpj mas forem objetos diferentes, entao pegamos o campo primario do segundo objeto e o colocamos no campo secundario da primeiro objeto.
                // Adicionamos o segundo objeto na lista para eliminação de objetos repetidos.
                if ( cliente.get(i).getCnpj().equals(cliente.get(j).getCnpj())){

                    listEliminarCliente.add(cliente.get(j));
                    i++; // Incrementa i em 1 para evitar verificações desnecessárias.
                    break;
                }
            }
        }
        //elimina todos os elementos que foram marcados para remoção
        cliente.removeAll(listEliminarCliente);
        return cliente.size();
    }

    private Integer countVendedor(List<Layout001> vendedor){

        List<Layout001> listEliminar = new ArrayList<>();

        // Faz um for percorrendo os itens da lista. Para cada item da lista percorrido, faz um outro for para percorrer todos os itens da lista novamente (Pulando o primeiro item do for externo)
        // para verificar qual objeto tem cpf igual ao objeto do for mais externo e se são objetos diferentes.
        for ( int i = 0; i < vendedor.size(); i++ ) {
            for ( int j = i + 1; j < vendedor.size(); j++ ) {
                // Se os objetos tiverem o mesmo cpf mas forem objetos diferentes, entao pegamos o campo primario do segundo objeto e o colocamos no campo secundario da primeiro objeto.
                // Adicionamos o segundo objeto na lista para eliminação de objetos repetidos.
                if ( vendedor.get(i).getCpf().equals(vendedor.get(j).getCpf())){

                    listEliminar.add(vendedor.get(j));
                    i++; // Incrementa i em 1 para evitar verificações desnecessárias.
                    break;
                }
            }
        }
        //elimina todos os elementos que foram marcados para remoção
        vendedor.removeAll(listEliminar);
        return vendedor.size();
    }

//    private String verificarVendaMaisCara(List<Layout003> vendas){
//
//        for(Layout003 venda : vendas){
//
//            for(Vendas v :venda.getVendas()){
//
//                BigDecimal precoItem = v.getPrecoItem();
//
//                Integer total = v.getPrecoItem() * v.getQuantidade();
//                System.out.println("ID: "+ v.getId() + "TOTAL DO ID" + total);
//            }
//
//        }
//
//
//        return "8";
    }

