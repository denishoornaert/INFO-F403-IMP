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
%p = alloca i32
%n = alloca i32
%1 = call i32 @readInt()
store i32 %1, i32* %n
%2 = call i32 @readInt()
store i32 %2, i32* %p
%3 = load i32, i32* %p
%4 = load i32, i32* %n
%5 = add i32 %4, %3
%6 = icmp sgt i32 %5, 20
%7 = load i32, i32* %p
%8 = icmp slt i32 %7, 1
%9 = load i32, i32* %n
%10 = icmp sgt i32 %9, 12
%11 = and i1 %10, %8
%12 = or i1 %11, %6
br i1 %12, label %if31, label %endif31
if31:
%13 = load i32, i32* %n
call void @println(i32 %13)
%14 = load i32, i32* %p
call void @println(i32 %14)
br label %endif31
endif31:
ret i32 0
}
