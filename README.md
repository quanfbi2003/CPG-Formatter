# Generate-CPG-Graph-Script

B1: Mở joern repl từ /root/bin/joern/joern-cli/ (giao diện joern-cli): ./joern -J-Xmx3G (Số Ram (3) bé hơn số ram set cho máy ảo 1 GB (4))


B2: importCode(inputPath="./x42/c", projectName="x42-c")

	
B3: Chỉnh sửa một số mục trong GenCpgWith14TypesOfEdges.scala trước khi gen, bao gồm:

- Line 41: cpggraph -> tên mình muốn (không khuyến nghị)
- Line 62: đường dẫn tới file output của data (khuyến nghị)

	
B4: Paste code đã chỉnh sửa vào joern repl và đợi.

# CPG-Formatter

Copy thư mục /dist vào nơi chứa folder data cần format và chạy là ok!!!

Formatted data sẽ nằm ở /dist/output...
