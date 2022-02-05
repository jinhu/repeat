# sample make file as input

all :

#----------------------------------------------------------------------#
#             Variables                                                #
#----------------------------------------------------------------------#

#----------------------------------------------------------------------#
#             SUN part                                                 #
#----------------------------------------------------------------------#
SUNTARGETSOL +=
SUNTARGETSOL += libWHAVxFC.so
libWHAVxFC.so,SRCS = WHAVxFCmet.c             \
                     WHAVxFC.c                \
                     WHAVxFCstim.c
libWHAVxFC.so,DEPSOL = libTHXA.so \
                       libFCXA.so \
                       libERXA.so

# Create this lib to allow python testcases to use WHAVPELR DDF-event.
SUNTARGETSOL += libWHAVPELR.so
libWHAVPELR.so,SRCS = WHAVPELRmet.c
libWHAVPELR.so,DEPSOL = libERXA.so \
                        libOOXA.so \
                        libPLXA.so \
                        libTHXA.so

# Create this lib to allow python testcases to use WHAVPEUR DDF-event.
SUNTARGETSOL += libWHAVPEUR.so
libWHAVPEUR.so,SRCS = WHAVPEURmet.c
libWHAVPEUR.so,DEPSOL = libERXA.so \
                        libOOXA.so \
                        libPLXA.so \
                        libTHXA.so

SUNTARGETSOL += libWHAVSPFT.so
libWHAVSPFT.so,SRCS = WHAVSPFT.c      \
                      WHAVSPFT_conv.c
libWHAVSPFT.so,DEPSOL = libWHAVxLOCATION.so   \
                      libWHAVXAxSECSe2sf.so \
                      libWHAVSPSU.so \
                      libWHAVSPER.so \
                      libPLXA.so   \
                      libFTXA.so   \
                      libTHXA.so   \
                      libERXA.so   \
                      libWHxCAR.so \
                      libWH30xCAR.so \
                      libWH30xSU.so

SUNTARGETSOL += libWHAVIOAX.so
libWHAVIOAX.so,SRCS = WHAVIOAX.c
libWHAVIOAX.so,DEPSOL = libPLXA.so        \
                      libWHAVSPER.so      \
                      libWHAVSPSU.so      \
                      libTHXA.so        \
                      libERXA.so        \
                      libWHAVXT .so        \
                      libGNEQ.so        \
                      libWHPDxAXIS.so   \
                      libWHPDxBRD.so    \
                      libWEVMxCU.so     \
                      libWEVMxTSU.so

SUNTARGETSOL += libWHAVIORO.so
libWHAVIORO.so,SRCS = WHAVIORO.c           \
                    WHAVIORO_config.c
libWHAVIORO.so,DEPSOL = libWHAVIOGI.so     \
                      libWHAVSPSU.so     \
                      libWHAVSPER.so     \
                      libWHAVSPMC.so     \
                      libPLXA.so       \
                      libTHXA.so       \
                      libERXA.so       \
                      libWHAVXT .so       \
                      libWHPDxAXIS.so  \
                      libWHPDxBRD.so   \
                      libWHPDxSIM.so   \
                      libWEVMxRB.so

SUNTARGETSOL += libWHAVSPEH.so
libWHAVSPEH.so,SRCS = WHAVSPEH.c
libWHAVSPEH.so,DEPSOL = libPLXA.so   \
                      libWHAVSPMC.so     \
                      libWHAVSPER.so \
                      libWHAVSPSU.so \
                      libERXA.so   \
                      libEHXA.so   \
                      libTHXA.so

SUNTARGETSOL += libWHAVSPMC.so
libWHAVSPMC.so,SRCS = WHAVSPMC.c                \
                    WHAVSPMC_fc.c             \
                    WHAVSPCA.c
libWHAVSPMC.so,DEPSOL = libWHAVSPER.so  \
                      libCMXA.so    \
                      libPLXA.so    \
                      libFCXA.so \
                      libWHAVSPSU.so  \
                      libWHAVXAxMC.so \
                      libWHAVXA.so    \
                      libWHAVXT .so    \
                      libTHXA.so    \
                      libERXA.so

SUNTARGETSOL += libWHAVSPSU.so
libWHAVSPSU.so,SRCS = WHAVSPSU_conv.c    \
                    WHAVSPSU_check.c   \
                    WHAVSPSU_conv_dt.c \
                    WHAVSPSU_timer.c   \
                    WHAVSPSU_state.c   \
                    WHAVSPSU_file.c    \
                    WHAVSPSU_sem.c     \
                    WHAVSPFU.c         \
                    WHAVSPSU_error_simulation.c
                    
libWHAVSPSU.so,DEPSOL = libWHAVSPER.so \
                      libCSIDXA.so   \
                      libDTXA.so   \
                      libERXA.so   \
                      libWHAVSPER.so \
                      libPLXA.so   \
                      libSYXA.so   \
                      libTHXA.so   \
                      libTHXAxGROUP.so \
                      libWHAVxLOCATION.so

SUNTARGETSOL += libWHAVPERO.so
libWHAVPERO.so,SRCS = WHAVPERO.c         \
                    WHAVPERO_ext.c     \
                    WHAVPERO_config.c  \
                    WHAVPERO_to_rotate_no_wafer.c  \
                    WHAVPERO_to_rotate_with_wafer.c  \
                    WHAVPERO_traject_no_wafer.c  \
                    WHAVPERO_traject_take_over_move.c  \
                    WHAVPERO_traject_with_wafer.c  \
                    WHAVPERO_without_rotate_no_wafer.c  \
                    WHAVPERO_without_rotate_with_wafer.c  
libWHAVPERO.so,DEPSOL = \
                      libDP.so       \
                      libERXA.so     \
                      libPLXA.so     \
                      libTHXA.so     \
                      libWHAVIOGI.so \
                      libWHAVIORO.so \
                      libWHAVSPEH.so \
                      libWHAVSPER.so \
                      libWHAVSPFT.so \
                      libWHAVSPMC.so \
                      libWHAVSPSU.so \
                      libWHAVXT.so  

SUNTARGETSOL += libWHAVIOES.so
libWHAVIOES.so,SRCS = WHAVIOES_conv.c \
                    WHAVIOES.c      \
                    WHAVIOES_bdi.c  \
                    WHAVIOES_dpr.c  \
                    WHAVIOES_esb.c  \
                    WHAVIOES_esb2.c \
                    WHAVIOES_shm.c  \
                    WHAVIOES_simulate.c 
libWHAVIOES.so,DEPSOL = libPLXA.so   \
                      libWHAVSPER.so \
                      libTHXA.so   \
                      libERXA.so   \
                      libWHAVXT .so   \
                      libWHAVSPSU.so \
                      libWHAVSPMC.so \
                      libDDXA.so     \
                      libWHAVIOAX.so \
                      libWEVMxES.so

SUNTARGETSOL += libWHAVIOGI.so
libWHAVIOGI.so,SRCS = WHAVIOGI.c          \
                    WHAVIOGI_globals.c  \
                    WHAVIOGI_bdi.c      \
                    WHAVIOGI_vacuum.c   \
                    WHAVIOGI_analog.c   \
                    WHAVIOGI_tracing.c  \
                    WHAVIOGI_simulate.c \
                    WHAVIOGI_conv.c     \
                    WHAVIOGI_config.c   \
                    WHAVIOGI_fprom.c    \
                    WHAVIOGI_fprom_gios_mk5.c \
                    WHAVIOGI_gios_mk5_support.c
                    
libWHAVIOGI.so,DEPSOL = libWHAVSPEH.so \
                      libPLXA.so   \
                      libCMXA      \
                      libWHAVSPER.so \
                      libWHAVSPSU.so \
                      libTHXA.so   \
                      libERXA.so   \
                      libWHAVSPSU.so \
                      libWHAVSPMC.so \
                      libDP.so     \
                      libWHAVXT .so  \
                      libWEVMxCL.so

SUNTARGETSOL += libWHAVIOMS.so
libWHAVIOMS.so,SRCS = WHAVIOMS.c          \
                    WHAVIOMS_sbip.c     \
                    WHAVIOMS_bdi.c      \
                    WHAVIOMS_conv.c     \
                    WHAVIOMS_simulate.c
libWHAVIOMS.so,DEPSOL = libPLXA.so   \
                      libWHAVSPER.so \
                      libWHAVSPSU.so \
                      libERXA.so   \
                      libWHAVXT .so   \
                      libTHXA.so

SUNTARGETSOL += libWHAVSPMA.so
libWHAVSPMA.so,SRCS = WHAVSPMA.c
libWHAVSPMA.so,DEPSOL = libGNMF.so   \
                      libGNEQ.so   \
                      libPLXA.so   \
                      libWHAVSPER.so \
                      libTHXA.so   \
                      libERXA.so   \
                      libWHAVSPSU.so

SUNTARGETSOL += libWHAVSPER.so
libWHAVSPER.so,SRCS = WHAVSPER.c
libWHAVSPER.so,DEPSOL = libPLXA.so        \
                      libTHXA.so        \
                      libERXA.so        \
                      libDDXAxOBJECT.so

SUNTARGETSOL += libWHAVAPDS.so
libWHAVAPDS.so,SRCS = WHAVSPDS.c          \
                    WHAVSPDS_wafer.c    \
                    WHAVSPDS_location.c \
                    WHAVSPDS_location_history.c \
                    WHAVSPDS_global.c   \
                    WHAVSPDS_lot.c   \
                    WHAVSPDS_fifo.c     \
                    WHAVSPDS_nv.c

libWHAVAPDS.so,DEPSOL = libPLXA.so   \
                      libTHXA.so   \
                      libERXA.so   \
                      libWHAVSPSU.so \
                      libWHAVSPER.so \
                      libDDXA.so \
                      libDDXAxOBJECT.so \
                      libDDXAxVARARRAY.so \
                      libDP.so 

libWHAVAPDS.so,ASPECTS = THxAOPxTRACE/THxAOPxTRACE.ac           # WeaveC

SUNTARGETSOL += libWHAVxDEVICEe2sf.so
libWHAVxDEVICEe2sf.so,SRCS = WHAVxDEVICEe2sf.c 

SUNTARGETSOL += libWHAVXAxWAFERDATAe2sf.so
libWHAVXAxWAFERDATAe2sf.so,SRCS = WHAVXAxWAFERDATAe2sf.c 

SUNTARGETSOL += libWHAVXAxSECSe2sf.so
libWHAVXAxSECSe2sf.so,SRCS = WHAVXAxSECSe2sf.c


SUNTARGETSOL += libWHAVIOGIxSIM.so
libWHAVIOGIxSIM.so,SRCS = WHAVIOGIxSIMmet.c \
                           WHAVIOGIxSIM_lib.c
libWHAVIOGIxSIM.so,DEPSOL = libTHXA.so \
                              libWHAVIOGI.so
libWHAVIOGIxSIM.so,ASPECTS = THxAOPxTRACE/THxAOPxTRACE.ac

#----------------------------------------------------------------------#
#             VxWorks 6.3 part                                         #
#----------------------------------------------------------------------#
VXppcTARGETLIB += libWHAVxFC_vwppc.a
libWHAVxFC_vwppc.a,SRCS = $(libWHAVxFC.so,SRCS)

VXppcTARGETLIB += libWHAVSPER_vwppc.a
libWHAVSPER_vwppc.a,SRCS = WHAVSPER.c

VXppcTARGETLIB += libWHAVIOAX_vwppc.a
libWHAVIOAX_vwppc.a,SRCS = $(libWHAVIOAX.so,SRCS)

VXppcTARGETLIB += libWHAVIORO_vwppc.a
libWHAVIORO_vwppc.a,SRCS = WHAVIORO.c        \
                         WHAVIORO_config.c

VXppcTARGETLIB += libWHAVSPEH_vwppc.a
libWHAVSPEH_vwppc.a,SRCS = WHAVSPEH.c

VXppcTARGETLIB += libWHAVSPMC_vwppc.a
libWHAVSPMC_vwppc.a,SRCS = $(libWHAVSPMC.so,SRCS)

VXppcTARGETLIB += libWHAVSPSU_vwppc.a
libWHAVSPSU_vwppc.a,SRCS = $(libWHAVSPSU.so,SRCS)

VXppcTARGETLIB += libWHAVPERO_vwppc.a
libWHAVPERO_vwppc.a,SRCS = $(libWHAVPERO.so,SRCS)

VXppcTARGETLIB += libWHAVIOES_vwppc.a
libWHAVIOES_vwppc.a,SRCS = WHAVIOES.c      \
                         WHAVIOES_conv.c \
                         WHAVIOES_bdi.c  \
                         WHAVIOES_esb.c  \
                         WHAVIOES_esb2.c \
                         WHAVIOES_dpr.c  \
                         WHAVIOES_shm.c  \
                         WHAVIOES_simulate.c

VXppcTARGETLIB += libWHAVIOGI_vwppc.a
libWHAVIOGI_vwppc.a,SRCS = $(libWHAVIOGI.so,SRCS)

VXppcTARGETLIB += libWHAVIOMS_vwppc.a
libWHAVIOMS_vwppc.a,SRCS = WHAVIOMS.c          \
                         WHAVIOMS_sbip.c     \
                         WHAVIOMS_bdi.c      \
                         WHAVIOMS_conv.c     \
                         WHAVIOMS_simulate.c

VXppcTARGETLIB += libWHAVSPMA_vwppc.a
libWHAVSPMA_vwppc.a,SRCS = WHAVSPMA.c

VXppcTARGETLIB += libWHAVSPFT_vwppc.a
libWHAVSPFT_vwppc.a,SRCS = WHAVSPFT.c      \
                         WHAVSPFT_conv.c

VXppcTARGETLIB += libWHAVAPDS_vwppc.a
libWHAVAPDS_vwppc.a,SRCS = WHAVSPDS.c          \
                         WHAVSPDS_wafer.c    \
                         WHAVSPDS_location.c \
                         WHAVSPDS_location_history.c \
                         WHAVSPDS_global.c   \
                         WHAVSPDS_lot.c   \
                         WHAVSPDS_fifo.c     \
                         WHAVSPDS_nv.c
libWHAVAPDS_vwppc.a,ASPECTS = THxAOPxTRACE/THxAOPxTRACE.ac           # WeaveC

VXppcTARGETLIB += libWHAVxDEVICEe2sf_vwppc.a
libWHAVxDEVICEe2sf_vwppc.a,SRCS = $(libWHAVxDEVICEe2sf.so,SRCS)

VXppcTARGETLIB += libWHAVXAxWAFERDATAe2sf_vwppc.a
libWHAVXAxWAFERDATAe2sf_vwppc.a,SRCS = $(libWHAVXAxWAFERDATAe2sf.so,SRCS)

VXppcTARGETLIB += libWHAVXAxSECSe2sf_vwppc.a
libWHAVXAxSECSe2sf_vwppc.a,SRCS = $(libWHAVXAxSECSe2sf.so,SRCS)

VXppcTARGETLIB += libWHAVIOGIxSIM_vwppc.a
libWHAVIOGIxSIM_vwppc.a,SRCS =$(libWHAVIOGIxSIM.so,SRCS)

#----------------------------------------------------------------------#
#             VxWorks 6.9 part                                         #
#----------------------------------------------------------------------#
VXppc500TARGETLIB += libWHAVxFC_vwppc500mc.a
libWHAVxFC_vwppc500mc.a,SRCS = $(libWHAVxFC.so,SRCS)

VXppc500TARGETLIB += libWHAVSPER_vwppc500mc.a
libWHAVSPER_vwppc500mc.a,SRCS = $(libWHAVSPER_vwppc.a,SRCS)

VXppc500TARGETLIB += libWHAVIOAX_vwppc500mc.a
libWHAVIOAX_vwppc500mc.a,SRCS = $(libWHAVIOAX.so,SRCS)

VXppc500TARGETLIB += libWHAVIORO_vwppc500mc.a
libWHAVIORO_vwppc500mc.a,SRCS = $(libWHAVIORO_vwppc.a,SRCS)

VXppc500TARGETLIB += libWHAVSPEH_vwppc500mc.a
libWHAVSPEH_vwppc500mc.a,SRCS = $(libWHAVSPEH_vwppc.a,SRCS)

VXppc500TARGETLIB += libWHAVSPMC_vwppc500mc.a
libWHAVSPMC_vwppc500mc.a,SRCS = $(libWHAVSPMC.so,SRCS)

VXppc500TARGETLIB += libWHAVSPSU_vwppc500mc.a
libWHAVSPSU_vwppc500mc.a,SRCS = $(libWHAVSPSU.so,SRCS)

VXppc500TARGETLIB += libWHAVPERO_vwppc500mc.a
libWHAVPERO_vwppc500mc.a,SRCS = $(libWHAVPERO.so,SRCS)

VXppc500TARGETLIB += libWHAVIOES_vwppc500mc.a
libWHAVIOES_vwppc500mc.a,SRCS = $(libWHAVIOES_vwppc.a,SRCS)

VXppc500TARGETLIB += libWHAVIOGI_vwppc500mc.a
libWHAVIOGI_vwppc500mc.a,SRCS = $(libWHAVIOGI.so,SRCS)

VXppc500TARGETLIB += libWHAVIOMS_vwppc500mc.a
libWHAVIOMS_vwppc500mc.a,SRCS = $(libWHAVIOMS_vwppc.a,SRCS)

VXppc500TARGETLIB += libWHAVSPMA_vwppc500mc.a
libWHAVSPMA_vwppc500mc.a,SRCS = $(libWHAVSPMA_vwppc.a,SRCS)

VXppc500TARGETLIB += libWHAVSPFT_vwppc500mc.a
libWHAVSPFT_vwppc500mc.a,SRCS = $(libWHAVSPFT_vwppc.a,SRCS)

VXppc500TARGETLIB += libWHAVAPDS_vwppc500mc.a
libWHAVAPDS_vwppc500mc.a,SRCS = $(libWHAVAPDS_vwppc.a,SRCS)

VXppc500TARGETLIB += libWHAVxDEVICEe2sf_vwppc500mc.a
libWHAVxDEVICEe2sf_vwppc500mc.a,SRCS = $(libWHAVxDEVICEe2sf.so,SRCS)

VXppc500TARGETLIB += libWHAVXAxWAFERDATAe2sf_vwppc500mc.a
libWHAVXAxWAFERDATAe2sf_vwppc500mc.a,SRCS = $(libWHAVXAxWAFERDATAe2sf.so,SRCS)

VXppc500TARGETLIB += libWHAVXAxSECSe2sf_vwppc500mc.a
libWHAVXAxSECSe2sf_vwppc500mc.a,SRCS = $(libWHAVXAxSECSe2sf.so,SRCS)

VXppc500TARGETLIB += libWHAVIOGIxSIM_vwppc500mc.a
libWHAVIOGIxSIM_vwppc500mc.a,SRCS =$(libWHAVIOGIxSIM.so,SRCS)

#----------------------------------------------------------------------#
#                           VXarma72smp part                           #
#----------------------------------------------------------------------#
VXarma72smpTARGETLIB += libWHAVxFC_vwarma72_smp.a
libWHAVxFC_vwarma72_smp.a,SRCS = $(libWHAVxFC.so,SRCS)

VXarma72smpTARGETLIB += libWHAVSPER_vwarma72_smp.a
libWHAVSPER_vwarma72_smp.a,SRCS = $(libWHAVSPER_vwppc.a,SRCS)

VXarma72smpTARGETLIB += libWHAVIOAX_vwarma72_smp.a
libWHAVIOAX_vwarma72_smp.a,SRCS = $(libWHAVIOAX.so,SRCS)

VXarma72smpTARGETLIB += libWHAVIORO_vwarma72_smp.a
libWHAVIORO_vwarma72_smp.a,SRCS = $(libWHAVIORO_vwppc.a,SRCS)
libWHAVIORO_vwarma72_smp.a,CFLAGS += -Wno-enum-conversion -Wno-tautological-pointer-compare

VXarma72smpTARGETLIB += libWHAVSPEH_vwarma72_smp.a
libWHAVSPEH_vwarma72_smp.a,SRCS = $(libWHAVSPEH_vwppc.a,SRCS)

VXarma72smpTARGETLIB += libWHAVSPMC_vwarma72_smp.a
libWHAVSPMC_vwarma72_smp.a,SRCS = $(libWHAVSPMC.so,SRCS)

VXarma72smpTARGETLIB += libWHAVSPSU_vwarma72_smp.a
libWHAVSPSU_vwarma72_smp.a,SRCS = $(libWHAVSPSU.so,SRCS)

VXarma72smpTARGETLIB += libWHAVPERO_vwarma72_smp.a
libWHAVPERO_vwarma72_smp.a,SRCS = $(libWHAVPERO.so,SRCS)
libWHAVPERO_vwarma72_smp.a,CFLAGS += -Wno-enum-conversion

VXarma72smpTARGETLIB += libWHAVIOES_vwarma72_smp.a
libWHAVIOES_vwarma72_smp.a,SRCS = $(libWHAVIOES_vwppc.a,SRCS)
libWHAVIOES_vwarma72_smp.a,CFLAGS += -Wno-sizeof-pointer-memaccess

VXarma72smpTARGETLIB += libWHAVIOGI_vwarma72_smp.a
libWHAVIOGI_vwarma72_smp.a,SRCS = $(libWHAVIOGI.so,SRCS)

VXarma72smpTARGETLIB += libWHAVIOMS_vwarma72_smp.a
libWHAVIOMS_vwarma72_smp.a,SRCS = $(libWHAVIOMS_vwppc.a,SRCS)

VXarma72smpTARGETLIB += libWHAVSPMA_vwarma72_smp.a
libWHAVSPMA_vwarma72_smp.a,SRCS = $(libWHAVSPMA_vwppc.a,SRCS)

VXarma72smpTARGETLIB += libWHAVSPFT_vwarma72_smp.a
libWHAVSPFT_vwarma72_smp.a,SRCS = $(libWHAVSPFT_vwppc.a,SRCS)

VXarma72smpTARGETLIB += libWHAVAPDS_vwarma72_smp.a
libWHAVAPDS_vwarma72_smp.a,SRCS = $(libWHAVAPDS_vwppc.a,SRCS)
libWHAVAPDS_vwarma72_smp.a,ASPECTS = THxAOPxTRACE/THxAOPxTRACE.ac           # WeaveC
libWHAVAPDS_vwarma72_smp.a,CFLAGS += -Wno-parentheses-equality

VXarma72smpTARGETLIB += libWHAVxDEVICEe2sf_vwarma72_smp.a
libWHAVxDEVICEe2sf_vwarma72_smp.a,SRCS = $(libWHAVxDEVICEe2sf.so,SRCS)

VXarma72smpTARGETLIB += libWHAVXAxWAFERDATAe2sf_vwarma72_smp.a
libWHAVXAxWAFERDATAe2sf_vwarma72_smp.a,SRCS = $(libWHAVXAxWAFERDATAe2sf.so,SRCS)

VXarma72smpTARGETLIB += libWHAVXAxSECSe2sf_vwarma72_smp.a
libWHAVXAxSECSe2sf_vwarma72_smp.a,SRCS = $(libWHAVXAxSECSe2sf.so,SRCS)

VXarma72smpTARGETLIB += libWHAVIOGIxSIM_vwarma72_smp.a
libWHAVIOGIxSIM_vwarma72_smp.a,SRCS =$(libWHAVIOGIxSIM.so,SRCS)


LNXppce500TARGETSOL +=


include $(CODEMGR_WS)/xscm/intlib.mk

