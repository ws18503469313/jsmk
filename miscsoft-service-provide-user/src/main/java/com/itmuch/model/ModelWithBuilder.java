package com.itmuch.model;

public class ModelWithBuilder {

    private Integer id;

    private String name;

    private User user;

    public static class Builder{
        private Integer id;

        private String name;

        private User user;

        public Builder id(Integer id){
            this.id = id;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder user(User user){
            this.user = user;
            return this;
        }

        public ModelWithBuilder build(){
            return new ModelWithBuilder(this);
        }
    }
    private ModelWithBuilder (Builder builder){
        this.id = builder.id;
        this.name = builder.name;
    }

    @Override
    public String toString() {
        return "ModelWithBuilder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
