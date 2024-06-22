import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.mfcc.MFCC;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;

import java.io.File;

public class main {
    public static void main(String[] args) {
        String audioFilePath = "path/to/audio.wav";
        File audioFile = new File(audioFilePath);

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromFile(audioFile, 1024, 512);

        // Extracting MFCC features
        MFCC mfcc = new MFCC(1024, 44100, 13, 50, 300, 3000);
        dispatcher.addAudioProcessor(mfcc);

        dispatcher.addAudioProcessor(new PitchProcessor(PitchEstimationAlgorithm.YIN, 44100, 1024,
                new PitchDetectionHandler() {
                    @Override
                    public void handlePitch(be.tarsos.dsp.pitch.PitchDetectionResult result, AudioEvent e) {
                        float pitch = result.getPitch();
                        if (pitch != -1) {
                            System.out.println("Pitch detected: " + pitch);
                        }
                    }
                }));

        dispatcher.run();
    }
}

