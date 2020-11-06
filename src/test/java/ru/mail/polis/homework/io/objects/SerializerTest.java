package ru.mail.polis.homework.io.objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SerializerTest {
    private static List<Animal> animals = new ArrayList<>();
    private static List<AnimalExternalizable> animalsExternalizable = new ArrayList<>();
    private static List<AnimalWithMethods> animalsWithMethods = new ArrayList<>();
    private static Random random = new Random();
    private static Serializer serializer = new Serializer();

    private static final int countOfAnimals = 5000;

    @BeforeAll
    static void initFields() {
        for (int i = 0; i < countOfAnimals; i++) {
            int randomSexIndex = random.nextInt(Animal.Sex.values().length);
            int randomNumberOfLimbs = random.nextInt(10);
            int randomEyeColorIndex = random.nextInt(Animal.Color.values().length);
            int randomMainColorIndex = random.nextInt(Animal.Color.values().length);
            int randomWeight = random.nextInt(200);
            String randomNickname = randomString(random.nextInt(10));
            int randomAnimalTypeIndex = random.nextInt(Animal.AnimalTypeClass.values().length);
            List<Integer> randomListSizes = new ArrayList<>();
            for (int j = 0; j < random.nextInt(10); j++) {
                randomListSizes.add(random.nextInt(20));
            }

            //Animal
            Animal animal = new Animal();
            animal.setSex(Animal.Sex.values()[randomSexIndex]);
            animal.setNumberOfLimbs(randomNumberOfLimbs);
            animal.setEyeColor(Animal.Color.values()[randomEyeColorIndex]);
            animal.setMainColor(Animal.Color.values()[randomMainColorIndex]);
            animal.setWeight(randomWeight);
            animal.setNickname(randomNickname);
            animal.setTypeAnimal(Animal.AnimalTypeClass.values()[randomAnimalTypeIndex]);
            animal.setAnimalSizes(randomListSizes);
            animals.add(animal);

            //AnimalExternalizable
            AnimalExternalizable animalExternalizable = new AnimalExternalizable();
            animalExternalizable.setSex(AnimalExternalizable.Sex.values()[randomSexIndex]);
            animalExternalizable.setNumberOfLimbs(randomNumberOfLimbs);
            animalExternalizable.setEyeColor(AnimalExternalizable.Color.values()[randomEyeColorIndex]);
            animalExternalizable.setMainColor(AnimalExternalizable.Color.values()[randomMainColorIndex]);
            animalExternalizable.setWeight(randomWeight);
            animalExternalizable.setNickname(randomNickname);
            animalExternalizable.setTypeAnimal(AnimalExternalizable.AnimalTypeClass.values()[randomAnimalTypeIndex]);
            animalExternalizable.setAnimalSizes(randomListSizes);
            animalsExternalizable.add(animalExternalizable);

            //AnimalWithMethods
            AnimalWithMethods animalWithMethods = new AnimalWithMethods();
            animalWithMethods.setSex(AnimalWithMethods.Sex.values()[randomSexIndex]);
            animalWithMethods.setNumberOfLimbs(randomNumberOfLimbs);
            animalWithMethods.setEyeColor(AnimalWithMethods.Color.values()[randomEyeColorIndex]);
            animalWithMethods.setMainColor(AnimalWithMethods.Color.values()[randomMainColorIndex]);
            animalWithMethods.setWeight(randomWeight);
            animalWithMethods.setNickname(randomNickname);
            animalWithMethods.setTypeAnimal(AnimalWithMethods.AnimalTypeClass.values()[randomAnimalTypeIndex]);
            animalWithMethods.setAnimalSizes(randomListSizes);
            animalsWithMethods.add(animalWithMethods);
        }
    }

    private static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int c = random.nextInt(74) + 48;
            sb.append((char) c);
        }
        return sb.toString();
    }

    @Test
    void defaultSerialize() throws IOException {
        long beginTime = System.currentTimeMillis();
        serializer.defaultSerialize(animals, "defaultSerializeTest.bin");
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("File defaultSerializeTest.bin (%d BYTE) was CREATED in %d mls",
                Files.size(Path.of("defaultSerializeTest.bin")),
                endTime - beginTime));
        Files.delete(Path.of("defaultSerializeTest.bin"));
    }

    @Test
    void defaultDeserialize() throws IOException {
        serializer.defaultSerialize(animals, "defaultDeserializeTest.bin");
        long beginTime = System.currentTimeMillis();
        List<Animal> deserializeList = serializer.defaultDeserialize("defaultDeserializeTest.bin");
        long endTime = System.currentTimeMillis();
        assertEquals(deserializeList, animals);
        System.out.println(String.format("File defaultDeserializeTest.bin (%d BYTE) was DESERIALIZED in %d mls",
                Files.size(Path.of("defaultDeserializeTest.bin")),
                endTime - beginTime));
        Files.delete(Path.of("defaultDeserializeTest.bin"));
    }

    @Test
    void serializeWithMethods() throws IOException {
        long beginTime = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, "serializeWithMethodsTest.bin");
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("File serializeWithMethodsTest.bin (%d BYTE) was CREATED in %d mls",
                Files.size(Path.of("serializeWithMethodsTest.bin")),
                endTime - beginTime));
        Files.delete(Path.of("serializeWithMethodsTest.bin"));
    }

    @Test
    void deserializeWithMethods() throws IOException {
        serializer.serializeWithMethods(animalsWithMethods, "deserializeWithMethodsTest.bin");
        long beginTime = System.currentTimeMillis();
        List<AnimalWithMethods> deserializeList = serializer.deserializeWithMethods("deserializeWithMethodsTest.bin");
        long endTime = System.currentTimeMillis();
        assertEquals(deserializeList, animalsWithMethods);
        System.out.println(String.format("File deserializeWithMethodsTest.bin (%d BYTE) was DESERIALIZED in %d mls",
                Files.size(Path.of("deserializeWithMethodsTest.bin")),
                endTime - beginTime));
        Files.delete(Path.of("deserializeWithMethodsTest.bin"));
    }

    @Test
    void serializeWithExternalizable() throws IOException {
        long beginTime = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalizable, "serializeWithExternalizableTest.bin");
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("File serializeWithExternalizableTest.bin (%d BYTE) was CREATED in %d mls",
                Files.size(Path.of("serializeWithExternalizableTest.bin")),
                endTime - beginTime));
        Files.delete(Path.of("serializeWithExternalizableTest.bin"));
    }

    @Test
    void deserializeWithExternalizable() throws IOException {
        serializer.serializeWithExternalizable(animalsExternalizable, "deserializeWithExternalizableTest.bin");
        long beginTime = System.currentTimeMillis();
        List<AnimalExternalizable> deserializeList =
                serializer.deserializeWithExternalizable("deserializeWithExternalizableTest.bin");
        long endTime = System.currentTimeMillis();
        assertEquals(deserializeList, animalsExternalizable);
        System.out.println(
                String.format("File deserializeWithExternalizableTest.bin (%d BYTE) was DESERIALIZED in %d mls",
                Files.size(Path.of("deserializeWithExternalizableTest.bin")),
                endTime - beginTime)
        );
        Files.delete(Path.of("deserializeWithExternalizableTest.bin"));
    }

    @Test
    void customSerialize() throws IOException {
        long beginTime = System.currentTimeMillis();
        serializer.customSerialize(animals, "customSerializeTest.bin");
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("File customSerializeTest.bin (%d BYTE) was CREATED in %d mls",
                Files.size(Path.of("customSerializeTest.bin")),
                endTime - beginTime));
        Files.delete(Path.of("customSerializeTest.bin"));
    }

    @Test
    void customDeserialize() throws IOException {
        serializer.customSerialize(animals, "customDeserializeTest.bin");
        long beginTime = System.currentTimeMillis();
        List<Animal> deserializeList =
                serializer.customDeserialize("customDeserializeTest.bin");
        long endTime = System.currentTimeMillis();
        assertEquals(deserializeList, animals);
        System.out.println(String.format("File customDeserializeTest.bin (%d BYTE) was DESERIALIZED in %d mls",
                Files.size(Path.of("customDeserializeTest.bin")),
                endTime - beginTime));
        Files.delete(Path.of("customDeserializeTest.bin"));
    }
}