    package  com.example.demo.dto;
    import lombok.AllArgsConstructor;
    import lombok.Data;

    @Data
    @AllArgsConstructor
    public class ApiResponse<T> {
        private T data;
        private ErrorDetails error;

        // Hàm tiện ích gọi cho nhanh
        public static <T> ApiResponse<T> success(T data) {
            return new ApiResponse<>(data, null);
        }
        
        public static <T> ApiResponse<T> error(String code, String message) {
            return new ApiResponse<>(null, new ErrorDetails(code, message));
        }
    }