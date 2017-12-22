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
%c = alloca i32
%1 = call i32 @readInt()
store i32 %1, i32* %a
%2 = call i32 @readInt()
store i32 %2, i32* %b
br label %startloop26
startloop26:
%3 = load i32, i32* %b
%4 = icmp ne i32 %3, 0
br i1 %4, label %loop26, label %endloop26
loop26:
%5 = load i32, i32* %b
store i32 %5, i32* %c
br label %startloop83
startloop83:
%6 = load i32, i32* %a
%7 = load i32, i32* %b
%8 = icmp sge i32 %6, %7
br i1 %8, label %loop83, label %endloop83
loop83:
%9 = load i32, i32* %b
%10 = load i32, i32* %a
%11 = sub i32 %10, %9
store i32 %11, i32* %a
br label %startloop83
endloop83:
%12 = load i32, i32* %a
store i32 %12, i32* %b
%13 = load i32, i32* %c
store i32 %13, i32* %a
br label %startloop26
endloop26:
%14 = load i32, i32* %a
call void @println(i32 %14)
ret i32 0
}