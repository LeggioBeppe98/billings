import { useMutation } from "@tanstack/react-query";
import { tokenStorage } from "@/shared/lib/token-storage";
import { loginRequest } from "../api/auth.api";

export function useLoginMutation() {
  return useMutation({
    mutationFn: loginRequest,
    onSuccess: (data) => {
      tokenStorage.setTokens(data.token, data.refreshToken);
    },
  });
}
