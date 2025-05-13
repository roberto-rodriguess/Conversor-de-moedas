import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.DoubleFunction;

public class Principal {
    private final static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        int opcao;
        do {
            mostrarMenu();
            opcao = input.nextInt();
            switch (opcao) {
                case 1 -> converter(Conversor::realParaDolar);
                case 2 -> converter(Conversor::dolarParaReal);
                case 3 -> converter(Conversor::realParaEuro);
                case 4 -> converter(Conversor::euroParaReal);
                case 5 -> converter(Conversor::dolarParaEuro);
                case 6 -> converter(Conversor::euroParaDolar);
                case 7 -> converter(Conversor::pesoArgentinoParaDolar);
                case 8 -> converter(Conversor::dolarParaPesoArgentino);
                case 9 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        } while (opcao != 9);
    }

    private static void converter(DoubleFunction<ConversaoMoeda> metodoConversao) {
        double valor = getValor();
        ConversaoMoeda resultado = metodoConversao.apply(valor);
        exibirConversao(valor, resultado);
    }

    private static double getValor() {
        while (true) {
            try {
                System.out.print("Digite o valor que deseja converter: ");
                return input.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, digite um número.");
                input.next(); // descarta entrada inválida
            }
        }
    }

    private static void exibirConversao(double valor, ConversaoMoeda valorConvertido) {
        if (valorConvertido == null) {
            System.out.println("Erro na conversão");
            return;
        }

        System.out.printf("O valor %,.2f [%s] corresponde ao valor de %,f [%s]%n%n",
                valor,
                valorConvertido.base_code(),
                valorConvertido.conversion_result(),
                valorConvertido.target_code()
        );
    }

    private static void mostrarMenu() {
        System.out.println("***************************************");
        System.out.println("Seja bem-vindo(a) ao Conversor de Moedas\n");

        System.out.println("1) Real brasileiro =>> Dólar");
        System.out.println("2) Dólar =>> Real brasileiro");
        System.out.println("3) Real brasileiro =>> Euro");
        System.out.println("4) Euro =>> Real brasileiro");
        System.out.println("5) Dólar =>> Euro");
        System.out.println("6) Euro =>> Dólar");
        System.out.println("7) Peso argentino =>> Dólar");
        System.out.println("8) Dólar =>> Peso argentino");
        System.out.println("9) Sair");
        System.out.println("Escolha uma opção válida: ");
        System.out.println("***************************************");
    }
}
