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
%tmpinc13 = alloca i32
%1 = add i32 0, 0
store i32 %1, i32* %tmpinc13
br label %startloop13
startloop13:
%2 = add i32 5, 0
%i = load i32, i32* %tmpinc13
%coundRes13 = icmp slt i32 %i, %2
br i1 %coundRes13, label %loop13, label %endloop13
loop13:
call void @println(i32 %i)
%tmpinc54 = alloca i32
%3 = add i32 1, 0
store i32 %3, i32* %tmpinc54
br label %startloop54
startloop54:
%4 = add i32 7, 0
%j = load i32, i32* %tmpinc54
%coundRes54 = icmp slt i32 %j, %4
br i1 %coundRes54, label %loop54, label %endloop54
loop54:
call void @println(i32 %j)
%inc54 = add i32 %j, 1
store i32 %inc54, i32* %tmpinc54
br label %startloop54
endloop54:
%inc13 = add i32 %i, 1
store i32 %inc13, i32* %tmpinc13
br label %startloop13
endloop13:
%5 = add i32 0, 0
%b = add i32 %5, 0
call void @println(i32 %b)
%tmpinc123 = alloca i32
%6 = add i32 4, 0
store i32 %6, i32* %tmpinc123
br label %startloop123
startloop123:
%7 = add i32 10, 0
%k = load i32, i32* %tmpinc123
%coundRes123 = icmp slt i32 %k, %7
br i1 %coundRes123, label %loop123, label %endloop123
loop123:
%8 = add i32 2, 0
call void @println(i32 %k)
%inc123 = add i32 %k, %8
store i32 %inc123, i32* %tmpinc123
br label %startloop123
endloop123:
%9 = add i32 0, 0
%c = add i32 %9, 0
call void @println(i32 %c)
%tmpinc202 = alloca i32
%10 = add i32 10, 0
store i32 %10, i32* %tmpinc202
br label %startloop202
startloop202:
%11 = add i32 12, 0
%l = load i32, i32* %tmpinc202
%coundRes202 = icmp slt i32 %l, %11
br i1 %coundRes202, label %loop202, label %endloop202
loop202:
call void @println(i32 %l)
%inc202 = add i32 %l, 1
store i32 %inc202, i32* %tmpinc202
br label %startloop202
endloop202:
ret i32 0
}
