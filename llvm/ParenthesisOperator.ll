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
%result = alloca i32
%normal = alloca i32
%parent = alloca i32
%result2 = alloca i32
%1 = mul i32 6, 3
%2 = add i32 5, %1
store i32 %2, i32* %normal
%3 = load i32, i32* %normal
call void @println(i32 %3)
%4 = add i32 5, 6
%5 = mul i32 %4, 3
store i32 %5, i32* %parent
%6 = load i32, i32* %parent
call void @println(i32 %6)
%7 = load i32, i32* %normal
%8 = load i32, i32* %parent
%9 = icmp ne i32 %7, %8
br i1 %9, label %if104, label %endif104
if104:
store i32 1, i32* %result
%10 = load i32, i32* %result
call void @println(i32 %10)
br label %endelse104
endif104:
store i32 0, i32* %result2
%11 = load i32, i32* %result2
call void @println(i32 %11)
br label %endelse104
endelse104:
ret i32 0
}
