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
%1 = call i32 @readInt()
store i32 %1, i32* %a
%2 = load i32, i32* %a
call void @println(i32 %2)
%3 = load i32, i32* %a
%4 = sub i32 0, %3
store i32 %4, i32* %b
%5 = load i32, i32* %b
call void @println(i32 %5)
%6 = load i32, i32* %b
%7 = icmp slt i32 %6, 0
%8 = load i32, i32* %a
%9 = icmp sgt i32 %8, 5
%10 = and i1 %9, %7
br i1 %10, label %if59, label %endif59
if59:
%11 = load i32, i32* %a
call void @println(i32 %11)
br label %endif59
endif59:
ret i32 0
}
