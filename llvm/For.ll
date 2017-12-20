@.str = private unnamed_addr constant [3 x i8] c"%d\00", align 1

define i32 @readInt() {
  %x = alloca i32, align 4
  %1 = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.str, i32 0, i32 0), i32* %x)
  %2 = load i32, i32* %x, align 4
  ret i32 %2
}

declare i32 @__isoc99_scanf(i8*, ...)
@.str2 = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define void @println(i32 %x) {
  %1 = alloca i32, align 4
  store i32 %x, i32* %1, align 4
  %2 = load i32, i32* %1, align 4
  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str2, i32 0, i32 0), i32 %2)
  ret void
}

declare i32 @printf(i8*, ...)
define i32 @main() {
%n = call i32 @readInt()
%tmpinc22 = alloca i32
%1 = add i32 0, 0
store i32 %1, i32* %tmpinc22
br label %startloop22
startloop22:
%2 = add i32 5, 0
%i = load i32, i32* %tmpinc22
%coundRes22 = icmp slt i32 %i, %2
br i1 %coundRes22, label %loop22, label %endloop22
loop22:
call void @println(i32 %i)
%inc22 = add i32 %i, 1
store i32 %inc22, i32* %tmpinc22
br label %startloop22
endloop22:
%tmpinc64 = alloca i32
%3 = add i32 1, 0
store i32 %3, i32* %tmpinc64
br label %startloop64
startloop64:
%4 = add i32 7, 0
%j = load i32, i32* %tmpinc64
%coundRes64 = icmp slt i32 %j, %4
br i1 %coundRes64, label %loop64, label %endloop64
loop64:
call void @println(i32 %j)
%inc64 = add i32 %j, 1
store i32 %inc64, i32* %tmpinc64
br label %startloop64
endloop64:
ret i32 0
}
