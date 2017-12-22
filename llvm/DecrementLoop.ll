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
%i = alloca i32
store i32 10, i32* %i
br label %startloop13
startloop13:
%1 = load i32, i32* %i
%coundRes13 = icmp slt i32 %1, 3
br i1 %coundRes13, label %loop13, label %endloop13
loop13:
%2 = sub i32 0, 2
%3 = load i32, i32* %i
call void @println(i32 %3)
%4 = load i32, i32* %i
%inc13 = add i32 %4, %2
store i32 %inc13, i32* %i
br label %startloop13
endloop13:
ret i32 0
}
