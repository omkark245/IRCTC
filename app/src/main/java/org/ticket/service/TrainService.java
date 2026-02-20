package org.ticket.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ticket.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainService {
    private List<Train> trainList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String TRAINS_PATH = "app/src/main/java/org/ticket/localdb/train.json";

    public TrainService() throws IOException {
        loadTrains();
    }

    public void loadTrains() throws IOException {
        File trainsFile = new File(TRAINS_PATH);
        if (!trainsFile.exists()) {
            trainList = new ArrayList<>();
        } else {
            trainList = objectMapper.readValue(trainsFile, new TypeReference<List<Train>>() {});
        }
    }

    public List<Train> searchTrains(String source, String destination) {
        return trainList.stream().filter(train -> {
            List<String> stations = train.getStations();
            int sourceIndex = stations.indexOf(source.toLowerCase());
            int destIndex = stations.indexOf(destination.toLowerCase());
            return sourceIndex != -1 && destIndex != -1 && sourceIndex < destIndex;
        }).collect(Collectors.toList());
    }

    public void updateTrain(Train train) {
        for (int i = 0; i < trainList.size(); i++) {
            if (trainList.get(i).getTrainId().equals(train.getTrainId())) {
                trainList.set(i, train);
                saveTrainListToFile();
                break;
            }
        }
    }

    private void saveTrainListToFile() {
        try {
            objectMapper.writeValue(new File(TRAINS_PATH), trainList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Train> getTrainList() {
        return trainList;
    }
}
