package animores.pet_service.profile.dao;


import animores.pet_service.profile.entity.Profile;

public record ProfileVo(
        String name,

        String imageUrl
) {
    public static ProfileVo fromProfile(Profile profile) {
        return new ProfileVo(
                profile.getName(),
                profile.getImageUrl());
    }
}
