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
%1 = add i32 0, 0
%a = add i32 %1, 0
%tmpinc72 = alloca i32
br label %startloop72
startloop72:
%i = load i32, i32* %tmpinc72
%coundRes72 = icmp slt i32 %i, %n
br i1 %coundRes72, label %loop72, label %endloop72
loop72:
call void @println(i32 %i)
%inc72 = add i32 %i, 1
store i32 %inc72, i32* %tmpinc72
br label %startloop72
endloop72:
ret i32 0
}