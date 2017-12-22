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
%sum = alloca i32
%1 = sdiv i32 90, 67
%2 = mul i32 8, %1
%3 = sdiv i32 7, %2
%4 = add i32 6, %3
%5 = add i32 5, %4
%6 = add i32 4, %5
%7 = add i32 3, %6
%8 = add i32 2, %7
%9 = add i32 1, %8
store i32 %9, i32* %sum
%10 = load i32, i32* %sum
call void @println(i32 %10)
ret i32 0
}