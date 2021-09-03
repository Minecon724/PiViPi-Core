package pl.pivipi.core;

import java.io.File;

import com.google.inject.Provides;

import dev.simplix.core.common.aop.AbstractSimplixModule;
import dev.simplix.core.common.aop.ApplicationModule;
import dev.simplix.core.common.aop.Private;
import dev.simplix.core.common.i18n.LocalizationManager;
import dev.simplix.core.common.i18n.LocalizationManagerFactory;

@ApplicationModule("PiViPi-Core")
public final class LocalizationModule extends AbstractSimplixModule {  
  
  @Provides  
  @Private
  public LocalizationManager localizationManager(LocalizationManagerFactory factory) {  
    return factory.create(new File("plugins/Core/lang"));  
  }
   
}
