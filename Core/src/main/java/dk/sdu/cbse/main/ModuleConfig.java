package dk.sdu.cbse.main;

import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

@Configuration
public class ModuleConfig {

    @Bean
    public ModuleLayer pluginLayer() {
        Path dir = Path.of("plugins");
        // Game still runs without plugins
        if (!Files.isDirectory(dir)) return ModuleLayer.boot();

        ModuleFinder finder = ModuleFinder.of(dir);
        Set<String> names = new HashSet<>();
        for (ModuleReference ref : finder.findAll()) {
            names.add(ref.descriptor().name());
        }
        if (names.isEmpty()) return ModuleLayer.boot();

        java.lang.module.Configuration cfg = ModuleLayer.boot().configuration()
                .resolveAndBind(finder, ModuleFinder.of(), names);
        return ModuleLayer.boot()
                .defineModulesWithOneLoader(cfg, ClassLoader.getSystemClassLoader());
    }

    @Bean
    public List<IGamePluginService> gamePlugins(ModuleLayer layer) {
        return load(layer, IGamePluginService.class);
    }

    @Bean
    public List<IEntityProcessingService> entityProcessors(ModuleLayer layer) {
        return load(layer, IEntityProcessingService.class);
    }

    @Bean
    public List<IPostEntityProcessingService> postProcessors(ModuleLayer layer) {
        return load(layer, IPostEntityProcessingService.class);
    }

    @Bean
    public Game game(List<IGamePluginService> gamePlugins,
                     List<IEntityProcessingService> entityProcessors,
                     List<IPostEntityProcessingService> postProcessors) {
        return new Game(gamePlugins, entityProcessors, postProcessors);
    }

    private static <T> List<T> load(ModuleLayer layer, Class<T> service) {
        List<T> result = new ArrayList<>();
        for (T s : ServiceLoader.load(layer, service)) {
            result.add(s);
        }
        return result;
    }
}
