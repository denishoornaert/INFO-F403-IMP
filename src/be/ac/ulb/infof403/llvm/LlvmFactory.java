package be.ac.ulb.infof403.llvm;

/**
 * 
 */
public class LlvmFactory {
    
    public static String getPrintMethod() {
        return "@.str2 = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1\n" +
                "\n" +
                "define void @println(i32 %x) {\n" +
                "  %1 = alloca i32, align 4\n" +
                "  store i32 %x, i32* %1, align 4\n" +
                "  %2 = load i32, i32* %1, align 4\n" +
                "  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str2, i32 0, "
                        + "i32 0), i32 %2)\n" +
                "  ret void\n" +
                "}\n" +
                "\n" +
                "declare i32 @printf(i8*, ...)\n";
    }
    
    public static String getReadIntMethod() {
        return "@.str = private unnamed_addr constant [3 x i8] c\"%d\\00\", align 1\n" +
                "\n" +
                "define i32 @readInt() {\n" +
                "  %x = alloca i32, align 4\n" +
                "  %1 = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.str, "
                        + "i32 0, i32 0), i32* %x)\n" +
                "  %2 = load i32, i32* %x, align 4\n" +
                "  ret i32 %2\n" +
                "}\n" +
                "\n" +
                "declare i32 @__isoc99_scanf(i8*, ...)\n";
    }
    
    public static String getVariablesAllocation() {
        return "";
    }
    
}
