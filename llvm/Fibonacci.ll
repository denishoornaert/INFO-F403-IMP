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
%a = alloca i32
%b = alloca i32
%tmp = alloca i32
%i = alloca i32
%n = alloca i32
%1 = call i32 @readInt()
store i32 %1, i32* %n
store i32 0, i32* %a
store i32 1, i32* %b
store i32 0, i32* %i
br label %startloop56
startloop56:
%2 = load i32, i32* %n
%3 = load i32, i32* %i
%coundRes56 = icmp slt i32 %3, %2
br i1 %coundRes56, label %loop56, label %endloop56
loop56:
%4 = load i32, i32* %b
%5 = load i32, i32* %a
%6 = add i32 %5, %4
store i32 %6, i32* %tmp
%7 = load i32, i32* %b
store i32 %7, i32* %a
%8 = load i32, i32* %tmp
store i32 %8, i32* %b
%9 = load i32, i32* %i
%inc56 = add i32 %9, 1
store i32 %inc56, i32* %i
br label %startloop56
endloop56:
%10 = load i32, i32* %b
call void @println(i32 %10)
ret i32 0
}