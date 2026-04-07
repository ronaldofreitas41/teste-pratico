import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {

        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        System.out.println("====== 3.1 - Inserção de Funcionários ======");
        // 3.1 - Inserir todos os funcionários
        System.out.println("Adicionando funcionário Maria...");
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        
        System.out.println("Adicionando funcionário João...");
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        
        System.out.println("Adicionando funcionário Caio...");
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        
        System.out.println("Adicionando funcionário Miguel...");
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        
        System.out.println("Adicionando funcionário Alice...");
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        
        System.out.println("Adicionando funcionário Heitor...");
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        
        System.out.println("Adicionando funcionário Arthur...");
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        
        System.out.println("Adicionando funcionário Laura...");
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        
        System.out.println("Adicionando funcionário Heloisa...");
        funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        
        System.out.println("Adicionando funcionário Helena...");
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        System.out.println("Total de funcionários inseridos: " + funcionarios.size() + "\n");

        // 3.2 - Remover o funcionário "João"
        funcionarios.removeIf(f -> f.getNome().equals("João"));
        System.out.println("====== 3.2 - Remoção de João ======");
        System.out.println("Funcionário 'João' removido. Total atual: " + funcionarios.size() + "\n");

        // 3.3 - Imprimir todos os funcionários com formatação
        System.out.println("====== 3.3 - Lista de Funcionários ======");
        imprimirFuncionarios(funcionarios);

        // 3.4 - Aumentar 10% de salário
        System.out.println("\n====== 3.4 - Aumento de 10% de Salário ======");
        funcionarios.forEach(f -> {
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.10"));
            f.setSalario(novoSalario);
        });
        System.out.println("Aumento aplicado a todos os funcionários.\n");

        // 3.5 e 3.6 - Agrupar por função e imprimir
        System.out.println("====== 3.5/3.6 - Agrupamento por Função ======");
        Map<String, List<Funcionario>> agrupadosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        agrupadosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\n" + funcao + ":");
            lista.forEach(f -> System.out.println("  - " + f.getNome() + " | " + formatarSalario(f.getSalario())));
        });

        // 3.8 - Imprimir aniversariantes dos meses 10 e 12
        System.out.println("\n====== 3.8 - Aniversariantes (Meses 10 e 12) ======");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 ||
                        f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println(f.getNome() + " - " +
                        f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

        // 3.9 - Imprimir o funcionário com maior idade
        System.out.println("\n====== 3.9 - Funcionário com Maior Idade ======");
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (maisVelho != null) {
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idade + " anos");
        }

        // 3.10 - Imprimir em ordem alfabética
        System.out.println("\n====== 3.10 - Lista Alfabética ======");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out
                        .println(f.getNome() + " | " + f.getFuncao() + " | " + formatarSalario(f.getSalario())));

        // 3.11 - Total de salários
        System.out.println("\n====== 3.11 - Total de Salários ======");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total: " + formatarSalario(totalSalarios));

        // 3.12 - Quantos salários mínimos cada um ganha
        System.out.println("\n====== 3.12 - Salários Mínimos ======");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> {
            BigDecimal quantidadeSM = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(f.getNome() + ": " + quantidadeSM.toString().replace(".", ",") + " SM");
        });
    }

    // Método auxiliar para imprimir funcionários com formatação
    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        funcionarios.forEach(f -> {
            System.out.println("Nome: " + f.getNome() +
                    " | Data: " + f.getDataNascimento().format(dtf) +
                    " | Salário: " + formatarSalario(f.getSalario()) +
                    " | Função: " + f.getFuncao());
        });
    }

    // Método auxiliar para formatar salário
    private static String formatarSalario(BigDecimal valor) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        String formatado = df.format(valor);
        return "R$ " + formatado.replace(",", "#").replace(".", ",").replace("#", ".");
    }
}