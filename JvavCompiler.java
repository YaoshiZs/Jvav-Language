import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JvavCompiler {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java JvavCompiler <input_file>");
            return;
        }

        String filename = args[0];
        String sourceCode = readSourceCode(filename);

        // 词法分析
        Lexer lexer = new Lexer(sourceCode);
        TokenStream tokens = lexer.tokenize();

        // 语法分析
        Parser parser = new Parser(tokens);
        ASTNode ast = parser.parse();

        // 语义分析
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        analyzer.analyze(ast);

        // 中间代码生成
        CodeGenerator generator = new CodeGenerator();
        IntermediateCode intermediateCode = generator.generate(ast);

        // 优化
        Optimizer optimizer = new Optimizer();
        optimizedIntermediateCode = optimizer.optimize(intermediateCode);

        // 目标代码生成
        TargetCodeGenerator targetCodeGenerator = new TargetCodeGenerator();
        byte[] targetCode = targetCodeGenerator.generate(optimizedIntermediateCode);

        // 写入目标文件
        String outputFile = filename.substring(0, filename.lastIndexOf('.')) + ".class";
        FileUtils.writeBinaryFile(outputFile, targetCode);

        System.out.println("Compilation completed.");
    }

    private static String readSourceCode(String filename) {
        StringBuilder sourceCode = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                sourceCode.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceCode.toString();
    }
}
