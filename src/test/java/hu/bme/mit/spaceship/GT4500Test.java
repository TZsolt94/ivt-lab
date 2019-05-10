package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockTs1;
  private TorpedoStore mockTs2;

  @BeforeEach
  public void init(){
    mockTs1 = mock(TorpedoStore.class);
    mockTs2 = mock(TorpedoStore.class);
    this.ship = new GT4500(mockTs1, mockTs2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockTs1.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTs1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockTs1.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTs2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_haveTorpedo(){
    // Arrange
    when(mockTs1.isEmpty()).thenReturn(false);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTs1, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_dontHaveTorpedo(){
    // Arrange
    when(mockTs1.isEmpty()).thenReturn(true);
    when(mockTs2.isEmpty()).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTs1, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_dontHaveTorpedoInPrimary(){
    // Arrange
    when(mockTs1.isEmpty()).thenReturn(true);
    when(mockTs2.isEmpty()).thenReturn(false);
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockTs2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_tryFireTwice(){
    // Arrange
    when(mockTs1.isEmpty()).thenReturn(true);
    when(mockTs2.isEmpty()).thenReturn(false);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockTs2, times(2)).fire(1);
  }

}
