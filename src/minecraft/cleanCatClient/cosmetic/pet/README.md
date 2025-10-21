# Wolf Pet Cosmetic

## Mô tả
Cosmetic pet sói được thiết kế để hiển thị trên vai người chơi, tương tự như các cosmetic hat và cape khác.

## Cấu trúc
- **CosmeticWolfPet.java**: Class chính kế thừa từ CosmeticBase, xử lý rendering
- **ModelWolfPet**: Model 3D của con sói với các bộ phận: body, head, tail, legs
- **PetScreen.java**: GUI để quản lý các pets
- **CosmeticBoolean**: Đã thêm wolf pet với type 4 (pets) và id 16

## Cách sử dụng
1. Mở CosmeticMainScreen
2. Click vào nút "Pets"
3. Click vào "Wolf Pet" để bật/tắt

## Texture
Texture được lưu tại: `src/minecraft/cleanCatClient/Cosmetic/pet/wolf.png`
- Kích thước: 64x32 pixels
- Format: PNG với alpha channel

## Tính năng
- Sói sẽ xuất hiện trên vai phải của người chơi
- Có animation đuôi nhẹ nhàng
- Theo dõi chuyển động đầu của người chơi một cách nhẹ nhàng
- Tự động ẩn khi người chơi đang sneak
- Scale 0.5x để phù hợp với kích thước vai

## Mở rộng
Để thêm pets khác:
1. Tạo class mới kế thừa từ CosmeticBase
2. Thêm vào CosmeticBoolean với type 4
3. Thêm vào addPetLayer() trong RenderPlayer
4. PetScreen sẽ tự động hiển thị pets mới
