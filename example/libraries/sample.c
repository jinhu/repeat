

#include "THXA/THXA.h"               /* trace handler */
#include <WHAV_class.h>
#include <WHAVAPGE.h>

#include <WHAV.h>
#include <WHAVSPER.h>
#include <WHAVPECHmet.h>
#include <WHAVSPMC.h>
#include <WHAVSPSU_conv.h>
#include <WHAVSPTH.h>
#include <WHAV_rq.h>
#include "WHxERRxLEG/WHXT_error_codes.h"

int WHAV_rq_set_mc_ch_slot_1_pos(
      /*@unused@*/ WHAV_object_t *object_p,
                   double      slot_1_pos,
                   char        purpose_str[])
{
   int  result     = OK;
   char *func_name = "WHAV_rq_set_mc_ch_slot_1_pos";

   THXAtrace(CC, THXA_TRACE_INT, func_name, "> ("
             "slot_1_pos=%g, "
             "purpose_str=%s"
             ")",
             slot_1_pos,
             purpose_str);

   if (OK == result)
   {
      result = WHAVPECH_set_mc_ch_slot_1_pos(slot_1_pos, purpose_str);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT(result);

   return result;
} /* End of WHAV_rq_set_mc_ch_slot_1_pos() */


/*#-------------------------------------------------------------------------#*/
int WHAV_rq_set_temp_mc_ch_slot_1_pos(
            /*@unused@*/ WHAV_object_t *object_p,
                         double      slot_1_pos)
/*  Description : Update the run-time carrier handler slot 1 position
 *                machine constant.
 *  In          : slot_1_pos  Slot 1 position
 *  InOut       : -
 *  Out         : -
 *  Returns     : OK
 *              : WHXT_SYSTEM_ERROR
 *  Pre.Cond.   :
 * [Post.Cond.  :]
 *  Notes       :
 * [Uses global Variables:]
 */
{
   char *func_name = "WHAV_rq_set_temp_mc_ch_slot_1_pos";
   int  result     = OK;

   THXAtrace(CC, THXA_TRACE_INT, func_name,
           "> ("
           "slot_1_pos=%g)",
           slot_1_pos);

   if (OK == result)
   {
      result = WHAVAPGE_set_temp_mc_ch_slot_1_pos(slot_1_pos);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "< ("
              ")=%s",
              WHAVSPER_name(result));

   return result;
} /* End WHAV_rq_set_temp_mc_ch_slot_1_pos */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_mc_ch_slot_1_pos(
      /*@unused@*/ WHAV_object_t *object_p,
                   double      *slot_1_pos_ptr)
/*  Description : Get the current carrier handler slot 1 position
 *                machine constant.
 *  In          : -
 *  InOut       : -
 *  Out         : slot_1_pos_ptr  Slot 1 position
 *  Returns     : OK
 *              : WHXT_SYSTEM_ERROR
 *  Pre.Cond.   :
 * [Post.Cond.  :]
 *  Notes       :
 * [Uses global Variables:]
 */
{
   int  result          = OK;
   char *func_name      = "WHAV_rq_get_mc_ch_slot_1_pos";

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (slot_1_pos_ptr=%s)",
              WHAVSPSU_pointer_to_string(slot_1_pos_ptr));

   if (OK == result)
   {
      result = WHAVPECH_get_mc_ch_slot_1_pos(slot_1_pos_ptr);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT_DOUBLE("slot_1_pos_ptr", slot_1_pos_ptr,
                             result);

   return result;
} /* End of WHAV_rq_get_mc_ch_slot_1_pos() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_default_mc_ch_slot_1_pos(
     /*@unused@*/WHAV_object_t *object_p,
                 double      *slot_1_pos_ptr)
/*  Description : Get the default carrier handler slot 1 position
 *                machine constant.
 *  In          : -
 *  InOut       : -
 *  Out         : slot_1_pos_ptr  Slot 1 position
 *  Returns     : OK
 *              : WHXT_SYSTEM_ERROR
 *  Pre.Cond.   : -
 * [Post.Cond.  :]
 *  Notes       : -
 * [Uses global Variables:]
 */
{
   char *func_name = "WHAV_rq_get_default_mc_ch_slot_1_pos";
   int  result     = OK;

   THXAtrace(CC, THXA_TRACE_INT, func_name,
           "> ("
           "slot_1_pos_ptr=%s)",
           WHAVSPSU_pointer_to_string(slot_1_pos_ptr));

   if (OK == result)
   {
      result = WHAVAPGE_get_default_mc_ch_slot_1_pos(slot_1_pos_ptr);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "< ("
              ")=%s",
              WHAVSPER_name(result));

   return result;
} /* End WHAV_rq_get_default_mc_ch_slot_1_pos */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_minimum_mc_ch_slot_1_pos(
     /*@unused@*/WHAV_object_t *object_p,
                 double      *slot_1_pos_ptr)
/*  Description : Get the minimum carrier handler slot 1 position
 *                machine constant.
 *  In          : -
 *  InOut       : -
 *  Out         : slot_1_pos_ptr  Slot 1 position
 *  Returns     : OK
 *              : WHXT_SYSTEM_ERROR
 *  Pre.Cond.   : -
 * [Post.Cond.  :]
 *  Notes       : -
 * [Uses global Variables:]
 */
{
   char *func_name = "WHAV_rq_get_minimum_mc_ch_slot_1_pos";
   int  result     = OK;

   THXAtrace(CC, THXA_TRACE_INT, func_name,
           "> ("
           "slot_1_pos_ptr=%s)",
           WHAVSPSU_pointer_to_string(slot_1_pos_ptr));

   if (OK == result)
   {
      result = WHAVAPGE_get_minimum_mc_ch_slot_1_pos(slot_1_pos_ptr);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "< ("
              ")=%s",
              WHAVSPER_name(result));

   return result;
} /* End WHAV_rq_get_minimum_mc_ch_slot_1_pos */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_maximum_mc_ch_slot_1_pos(
     /*@unused@*/WHAV_object_t *object_p,
                 double      *slot_1_pos_ptr)
/*  Description : Get the maximum carrier handler slot 1 position
 *                machine constant.
 *  In          : -
 *  InOut       : -
 *  Out         : slot_1_pos_ptr  Slot 1 position
 *  Returns     : OK
 *              : WHXT_SYSTEM_ERROR
 *  Pre.Cond.   : -
 * [Post.Cond.  :]
 *  Notes       : -
 * [Uses global Variables:]
 */
{
   char *func_name = "WHAV_rq_get_maximum_mc_ch_slot_1_pos";
   int  result     = OK;

   THXAtrace(CC, THXA_TRACE_INT, func_name,
           "> ("
           "slot_1_pos_ptr=%s)",
           WHAVSPSU_pointer_to_string(slot_1_pos_ptr));

   if (OK == result)
   {
      result = WHAVAPGE_get_maximum_mc_ch_slot_1_pos(slot_1_pos_ptr);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "< ("
              ")=%s",
              WHAVSPER_name(result));

   return result;
} /* End WHAV_rq_get_maximum_mc_ch_slot_1_pos */


/*-------------------------------------------------------------------------*/
int WHAV_rq_set_mc_ch_inv_snsr_dist(
               /*@unused@*/ WHAV_object_t *object_p,
                            double      sensor_dist_a,
                            double      mid_wafer_1_dist_b,
                            double      mid_slot_1_dist_c,
                            double      sensor_dist_d,
                            double      empty_slot_dist_f,
                            double      sensor_dist_s0,
                            char        purpose_str[])
/*  Description : Update the current carrier handler sensor distance
 *                machine constants.
 *  In          : sensor_dist_a       Sensor distance a value
 *                mid_wafer_1_dist_b  Mid wafer 1 distance b value
 *                mid_slot_1_dist_c   Mid slot 1 distance c value
 *                sensor_dist_d       Sensor distance d value
 *                empty_slot_dist_f   Empty slot distance f value
 *                sensor_dist_s0      Sensor distance s0 value
 *                purpose_str         Reason for update
 *  InOut       : -
 *  Out         : -
 *  Returns     : OK
 *              : WHXT_SYSTEM_ERROR
 *  Pre.Cond.   : -
 * [Post.Cond.  :]
 *  Notes       : -
 * [Uses global Variables:]
 */
{
   int                         result     = OK;
   char                        *func_name = "WHAV_rq_set_mc_ch_inv_snsr_dist";
   WHAVXAxMC_ch_constants_struct *ch_mc_ptr = NULL;

   THXAtrace(CC, THXA_TRACE_INT, func_name, "> ("
             "sensor_dist_a=%g, "
             "mid_wafer_1_dist_b=%g, "
             "mid_slot_1_dist_c=%g, "
             "sensor_dist_d=%g, "
             "empty_slot_dist_f=%g, "
             "sensor_dist_s0=%g, "
             "purpose_str[]=%s"
             ")",
             sensor_dist_a,
             mid_wafer_1_dist_b,
             mid_slot_1_dist_c,
             sensor_dist_d,
             empty_slot_dist_f,
             sensor_dist_s0,
             purpose_str);

   if (OK == result)
   {
      result = WHAVSPMC_get_ch_constants(&ch_mc_ptr);
   }

   if (OK == result)
   {
      ch_mc_ptr->sensor_dist_a = sensor_dist_a;
      ch_mc_ptr->sensor_dist_b = mid_wafer_1_dist_b;
      ch_mc_ptr->sensor_dist_c = mid_slot_1_dist_c;
      ch_mc_ptr->sensor_dist_d = sensor_dist_d;
      ch_mc_ptr->sensor_dist_f = empty_slot_dist_f;
      ch_mc_ptr->sensor_dist_s0 = sensor_dist_s0;
      result = WHAVSPMC_set_ch_constants(ch_mc_ptr, purpose_str);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT(result);

   return result;
} /* End of WHAV_rq_set_mc_ch_inv_snsr_dist() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_set_temp_mc_ch_inv_snsr_dist(
            /*@unused@*/ WHAV_object_t *object_p,
                         double      sensor_dist_a,
                         double      mid_wafer_1_dist_b,
                         double      mid_slot_1_dist_c,
                         double      sensor_dist_d,
                         double      empty_slot_dist_f,
                         double      sensor_dist_s0)
/*  Description : Update the run-time carrier handler sensor distance
 *                machine constants.
 *  In          : sensor_dist_a       Sensor distance a value
 *                mid_wafer_1_dist_b  Mid wafer 1 distance b value
 *                mid_slot_1_dist_c   Mid slot 1 distance c value
 *                sensor_dist_d       Sensor distance d value
 *                empty_slot_dist_f   Empty slot distance f value
 *                sensor_dist_s0      Sensor distance s0 value
 *  InOut       : -
 *  Out         : -
 *  Returns     : OK
 *              : WHXT_SYSTEM_ERROR
 *  Pre.Cond.   :
 * [Post.Cond.  :]
 *  Notes       :
 * [Uses global Variables:]
 */
{
   char *func_name = "WHAV_rq_set_temp_mc_ch_inv_snsr_dist";
   int  result     = OK;

   THXAtrace(CC, THXA_TRACE_INT, func_name,
           "> ("
           "sensor_dist_a=%g, "
           "mid_wafer_1_dist_b=%g, "
           "mid_slot_1_dist_c=%g, "
           "sensor_dist_d=%g, "
           "empty_slot_dist_f=%g, "
           "sensor_dist_s0=%g)",
           sensor_dist_a,
           mid_wafer_1_dist_b,
           mid_slot_1_dist_c,
           sensor_dist_d,
           empty_slot_dist_f,
           sensor_dist_s0);

   if (OK == result)
   {
      result = WHAVAPGE_set_temp_mc_ch_inv_snsr_dist(
                                sensor_dist_a,
                                mid_wafer_1_dist_b,
                                mid_slot_1_dist_c,
                                sensor_dist_d,
                                empty_slot_dist_f,
                                sensor_dist_s0);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "< ("
              ")=%s",
              WHAVSPER_name(result));

   return result;
} /* End WHAV_rq_set_temp_mc_ch_inv_snsr_dist */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_mc_ch_inv_snsr_dist(
               /*@unused@*/ WHAV_object_t *object_p,
                            double      *sensor_dist_a_ptr,
                            double      *mid_wafer_1_dist_b_ptr,
                            double      *mid_slot_1_dist_c_ptr,
                            double      *sensor_dist_d_ptr,
                            double      *empty_slot_dist_f_ptr,
                            double      *sensor_dist_s0_ptr)
/*
 *  Input(s)      :  -
 *  Output(s)     :  sensor_dist_a_ptr      Sensor distance a value
 *                   mid_wafer_1_dist_b_ptr Mid wafer 1 distance b value
 *                   mid_slot_1_dist_c_ptr  Mid slot 1 distance c value
 *                   sensor_dist_d_ptr      Sensor distance d value
 *                   empty_slot_dist_f_ptr  Empty slot distance f value
 *                   sensor_dist_s0_ptr     Sensor distance s0 value
 *  InOut(s)      :  -
 *  Returns       :  OK
 *                   WHXT_SYSTEM_ERROR
 *  Preconditions :  -
 *  Postconditions:  -
 *  Notes         :  -
 */
{
   int                         result     = OK;
   char                        *func_name = "WHAV_rq_get_mc_ch_inv_snsr_dist";
   WHAVXAxMC_ch_constants_struct *ch_mc_ptr = NULL;

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (sensor_dist_a_ptr=%s, "
              "mid_wafer_1_dist_b_ptr=%s, "
              "mid_slot_1_dist_c_ptr=%s, "
              "sensor_dist_d_ptr=%s, "
              "empty_slot_dist_f_ptr=%s, "
              "sensor_dist_s0_ptr=%s"
              ")",
              WHAVSPSU_pointer_to_string(sensor_dist_a_ptr),
              WHAVSPSU_pointer_to_string(mid_wafer_1_dist_b_ptr),
              WHAVSPSU_pointer_to_string(mid_slot_1_dist_c_ptr),
              WHAVSPSU_pointer_to_string(sensor_dist_d_ptr),
              WHAVSPSU_pointer_to_string(empty_slot_dist_f_ptr),
              WHAVSPSU_pointer_to_string(sensor_dist_s0_ptr));

   if (OK == result)
   {
      result = WHAVSPMC_get_ch_constants(&ch_mc_ptr);
   }

   if (OK == result)
   {
      *sensor_dist_a_ptr      = ch_mc_ptr->sensor_dist_a;
      *mid_wafer_1_dist_b_ptr = ch_mc_ptr->sensor_dist_b;
      *mid_slot_1_dist_c_ptr  = ch_mc_ptr->sensor_dist_c;
      *sensor_dist_d_ptr      = ch_mc_ptr->sensor_dist_d;
      *empty_slot_dist_f_ptr  = ch_mc_ptr->sensor_dist_f;
      *sensor_dist_s0_ptr     = ch_mc_ptr->sensor_dist_s0;
   }

   WHAVSPTH_TRACE_VAR("sensor_dist_a=%g",          *sensor_dist_a_ptr);
   WHAVSPTH_TRACE_VAR("mid_wafer_1_dist_b_ptr=%g", *mid_wafer_1_dist_b_ptr);
   WHAVSPTH_TRACE_VAR("mid_slot_1_dist_c_ptr=%g",  *mid_slot_1_dist_c_ptr);
   WHAVSPTH_TRACE_VAR("sensor_dist_d_ptr=%g",      *sensor_dist_d_ptr);
   WHAVSPTH_TRACE_VAR("empty_slot_dist_f_ptr=%g",  *empty_slot_dist_f_ptr);
   WHAVSPTH_TRACE_VAR("sensor_dist_s0_ptr=%g",     *sensor_dist_s0_ptr);

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT(result);

   return result;
} /* End of WHAV_rq_get_mc_ch_inv_snsr_dist() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_default_mc_ch_inv_snsr_dist(
     /*@unused@*/ WHAV_object_t *object_p,
                  double      *sensor_dist_a_ptr,
                  double      *mid_wafer_1_dist_b_ptr,
                  double      *mid_slot_1_dist_c_ptr,
                  double      *sensor_dist_d_ptr,
                  double      *empty_slot_dist_f_ptr,
                  double      *sensor_dist_s0_ptr)
/*
 *  Input(s)      :  -
 *  Output(s)     :  sensor_dist_a_ptr      Sensor distance a value
 *                   mid_wafer_1_dist_b_ptr Mid wafer 1 distance b value
 *                   mid_slot_1_dist_c_ptr  Mid slot 1 distance c value
 *                   sensor_dist_d_ptr      Sensor distance d value
 *                   empty_slot_dist_f_ptr  Empty slot distance f value
 *                   sensor_dist_s0_ptr     Sensor distance s0 value
 *  InOut(s)      :  -
 *  Returns       :  OK
 *                   WHXT_SYSTEM_ERROR
 *  Preconditions :  -
 *  Postconditions:  -
 *  Notes         :  -
 */
{
   int  result     = OK;
   char *func_name = "WHAV_rq_get_default_mc_ch_inv_snsr_dist";

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (sensor_dist_a_ptr=%s, "
              "mid_wafer_1_dist_b_ptr=%s, "
              "mid_slot_1_dist_c_ptr=%s, "
              "sensor_dist_d_ptr=%s, "
              "empty_slot_dist_f_ptr=%s, "
              "sensor_dist_s0_ptr=%s"
              ")",
              WHAVSPSU_pointer_to_string(sensor_dist_a_ptr),
              WHAVSPSU_pointer_to_string(mid_wafer_1_dist_b_ptr),
              WHAVSPSU_pointer_to_string(mid_slot_1_dist_c_ptr),
              WHAVSPSU_pointer_to_string(sensor_dist_d_ptr),
              WHAVSPSU_pointer_to_string(empty_slot_dist_f_ptr),
              WHAVSPSU_pointer_to_string(sensor_dist_s0_ptr));

   if (OK == result)
   {
      result = WHAVAPGE_get_default_mc_ch_inv_snsr_dist(sensor_dist_a_ptr,
                                                      mid_wafer_1_dist_b_ptr,
                                                      mid_slot_1_dist_c_ptr,
                                                      sensor_dist_d_ptr,
                                                      empty_slot_dist_f_ptr ,
                                                      sensor_dist_s0_ptr);
   }

   WHAVSPTH_TRACE_VAR("sensor_dist_a=%g",            *sensor_dist_a_ptr);
   WHAVSPTH_TRACE_VAR("mid_wafer_1_dist_b_ptr=%g",   *mid_wafer_1_dist_b_ptr);
   WHAVSPTH_TRACE_VAR("mid_slot_1_dist_c_ptr=%g",    *mid_slot_1_dist_c_ptr);
   WHAVSPTH_TRACE_VAR("sensor_dist_d_ptr=%g",        *sensor_dist_d_ptr);
   WHAVSPTH_TRACE_VAR("empty_slot_dist_f_ptr=%g",    *empty_slot_dist_f_ptr);
   WHAVSPTH_TRACE_VAR("sensor_dist_s0_ptr=%g",       *sensor_dist_s0_ptr);

   WHAVSPER_LC (result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT(result);

   return result;
} /* End of WHAV_rq_get_default_mc_ch_inv_snsr_dist() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_minimum_mc_ch_inv_snsr_dist(
     /*@unused@*/ WHAV_object_t *object_p,
                  double      *sensor_dist_a_ptr,
                  double      *mid_wafer_1_dist_b_ptr,
                  double      *mid_slot_1_dist_c_ptr,
                  double      *sensor_dist_d_ptr,
                  double      *empty_slot_dist_f_ptr,
                  double      *sensor_dist_s0_ptr)
/*
 *  Input(s)      :  -
 *  Output(s)     :  sensor_dist_a_ptr      Sensor distance a value
 *                   mid_wafer_1_dist_b_ptr Mid wafer 1 distance b value
 *                   mid_slot_1_dist_c_ptr  Mid slot 1 distance c value
 *                   sensor_dist_d_ptr      Sensor distance d value
 *                   empty_slot_dist_f_ptr  Empty slot distance f value
 *                   sensor_dist_s0_ptr     Sensor distance s0 value
 *  InOut(s)      :  -
 *  Returns       :  OK
 *                   WHXT_SYSTEM_ERROR
 *  Preconditions :  -
 *  Postconditions:  -
 *  Notes         :  -
 */
{
   int  result     = OK;
   char *func_name = "WHAV_rq_get_minimum_mc_ch_inv_snsr_dist";

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (sensor_dist_a_ptr=%s, "
              "mid_wafer_1_dist_b_ptr=%s, "
              "mid_slot_1_dist_c_ptr=%s, "
              "sensor_dist_d_ptr=%s, "
              "empty_slot_dist_f_ptr=%s, "
              "sensor_dist_s0_ptr=%s"
              ")",
              WHAVSPSU_pointer_to_string(sensor_dist_a_ptr),
              WHAVSPSU_pointer_to_string(mid_wafer_1_dist_b_ptr),
              WHAVSPSU_pointer_to_string(mid_slot_1_dist_c_ptr),
              WHAVSPSU_pointer_to_string(sensor_dist_d_ptr),
              WHAVSPSU_pointer_to_string(empty_slot_dist_f_ptr),
              WHAVSPSU_pointer_to_string(sensor_dist_s0_ptr));

   if (OK == result)
   {
      result = WHAVAPGE_get_minimum_mc_ch_inv_snsr_dist(sensor_dist_a_ptr,
                                                      mid_wafer_1_dist_b_ptr,
                                                      mid_slot_1_dist_c_ptr,
                                                      sensor_dist_d_ptr,
                                                      empty_slot_dist_f_ptr ,
                                                      sensor_dist_s0_ptr);
   }

   WHAVSPTH_TRACE_VAR("sensor_dist_a=%g",            *sensor_dist_a_ptr);
   WHAVSPTH_TRACE_VAR("mid_wafer_1_dist_b_ptr=%g",   *mid_wafer_1_dist_b_ptr);
   WHAVSPTH_TRACE_VAR("mid_slot_1_dist_c_ptr=%g",    *mid_slot_1_dist_c_ptr);
   WHAVSPTH_TRACE_VAR("sensor_dist_d_ptr=%g",        *sensor_dist_d_ptr);
   WHAVSPTH_TRACE_VAR("empty_slot_dist_f_ptr=%g",    *empty_slot_dist_f_ptr);
   WHAVSPTH_TRACE_VAR("sensor_dist_s0_ptr=%g",       *sensor_dist_s0_ptr);

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT(result);

   return result;
} /* End of WHAV_rq_get_minimum_mc_ch_inv_snsr_dist() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_maximum_mc_ch_inv_snsr_dist(
     /*@unused@*/ WHAV_object_t *object_p,
                  double      *sensor_dist_a_ptr,
                  double      *mid_wafer_1_dist_b_ptr,
                  double      *mid_slot_1_dist_c_ptr,
                  double      *sensor_dist_d_ptr,
                  double      *empty_slot_dist_f_ptr,
                  double      *sensor_dist_s0_ptr)
/*
 *  Input(s)      :  -
 *  Output(s)     :  sensor_dist_a_ptr      Sensor distance a value
 *                   mid_wafer_1_dist_b_ptr Mid wafer 1 distance b value
 *                   mid_slot_1_dist_c_ptr  Mid slot 1 distance c value
 *                   sensor_dist_d_ptr      Sensor distance d value
 *                   empty_slot_dist_f_ptr  Empty slot distance f value
 *                   sensor_dist_s0_ptr     Sensor distance s0 value
 *  InOut(s)      :  -
 *  Returns       :  OK
 *                   WHXT_SYSTEM_ERROR
 *  Preconditions :  -
 *  Postconditions:  -
 *  Notes         :  -
 */
{
   int  result     = OK;
   char *func_name = "WHAV_rq_get_maximum_mc_ch_inv_snsr_dist";

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (sensor_dist_a_ptr=%s, "
              "mid_wafer_1_dist_b_ptr=%s, "
              "mid_slot_1_dist_c_ptr=%s, "
              "sensor_dist_d_ptr=%s, "
              "empty_slot_dist_f_ptr=%s, "
              "sensor_dist_s0_ptr=%s"
              ")",
              WHAVSPSU_pointer_to_string(sensor_dist_a_ptr),
              WHAVSPSU_pointer_to_string(mid_wafer_1_dist_b_ptr),
              WHAVSPSU_pointer_to_string(mid_slot_1_dist_c_ptr),
              WHAVSPSU_pointer_to_string(sensor_dist_d_ptr),
              WHAVSPSU_pointer_to_string(empty_slot_dist_f_ptr),
              WHAVSPSU_pointer_to_string(sensor_dist_s0_ptr));

   if (OK == result)
   {
      result = WHAVAPGE_get_maximum_mc_ch_inv_snsr_dist(sensor_dist_a_ptr,
                                                      mid_wafer_1_dist_b_ptr,
                                                      mid_slot_1_dist_c_ptr,
                                                      sensor_dist_d_ptr,
                                                      empty_slot_dist_f_ptr ,
                                                      sensor_dist_s0_ptr);
   }

   WHAVSPTH_TRACE_VAR("sensor_dist_a=%g",            *sensor_dist_a_ptr);
   WHAVSPTH_TRACE_VAR("mid_wafer_1_dist_b_ptr=%g",   *mid_wafer_1_dist_b_ptr);
   WHAVSPTH_TRACE_VAR("mid_slot_1_dist_c_ptr=%g",    *mid_slot_1_dist_c_ptr);
   WHAVSPTH_TRACE_VAR("sensor_dist_d_ptr=%g",        *sensor_dist_d_ptr);
   WHAVSPTH_TRACE_VAR("empty_slot_dist_f_ptr=%g",    *empty_slot_dist_f_ptr);
   WHAVSPTH_TRACE_VAR("sensor_dist_s0_ptr=%g",       *sensor_dist_s0_ptr);

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT(result);

   return result;
} /* End of WHAV_rq_get_maximum_mc_ch_inv_snsr_dist() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_set_mc_ch_cross_slot_criteria(
                        /*@unused@*/ WHAV_object_t *object_p,
                                     double       cross_slot_criteria,
                                     char         purpose_str[])
/*  Description   : Update the current carrier handler cross slot criteria
 *                  machine constant.
 *  Input(s)      : cross_slot_criteria Cross slot criteria
 *                  purpose_str         Reason for update
 *  Output(s)     : -
 *  InOut(s)      : -
 *  Returns       : OK
 *                  WHXT_SYSTEM_ERROR
 *  Preconditions : -
 *  Postconditions: -
 *  Notes         : -
 */
{
   int                         result     = OK;
   char                        *func_name = "WHAV_rq_set_mc_ch_cross_slot_criteria";
   WHAVXAxMC_ch_constants_struct *ch_mc_ptr = NULL;

   THXAtrace(CC, THXA_TRACE_INT, func_name, "> ("
             "cross_slot_criteria=%g, "
             "purpose_str=%s"
             ")",
             cross_slot_criteria,
             purpose_str);

   if (OK == result)
   {
      result = WHAVSPMC_get_ch_constants(&ch_mc_ptr);
   }

   if (OK == result)
   {
      ch_mc_ptr->cross_slot_criteria = cross_slot_criteria;
      result = WHAVSPMC_set_ch_constants(ch_mc_ptr, purpose_str);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT(result);

   return result;
} /* End of WHAV_rq_set_mc_ch_cross_slot_criteria() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_set_temp_mc_ch_cross_slot_criteria(
            /*@unused@*/ WHAV_object_t  *object_p,
                         double cross_slot_criteria)
/*  Description : Update the run-time carrier handler cross slot criteria
 *                machine constant.
 *  In          : cross_slot_criteria  Cross slot criteria value
 *  InOut       : -
 *  Out         : -
 *  Returns     : OK
 *              : WHXT_SYSTEM_ERROR
 *  Pre.Cond.   : -
 * [Post.Cond.  :]
 *  Notes       : -
 * [Uses global Variables:]
 */
{
   char *func_name = "WHAV_rq_set_temp_mc_ch_cross_slot_criteria";
   int  result     = OK;

   THXAtrace(CC, THXA_TRACE_INT, func_name,
           "> ("
           "cross_slot_criteria=%g)",
           cross_slot_criteria);

   if (OK == result)
   {
      result = WHAVAPGE_set_temp_mc_ch_cross_slot_criteria(cross_slot_criteria);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "< ("
              ")=%s",
              WHAVSPER_name(result));

   return result;
} /* End WHAV_rq_set_temp_mc_ch_cross_slot_criteria */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_mc_ch_cross_slot_criteria(
                  /*@unused@*/ WHAV_object_t *object_p,
                               double      *cross_slot_criteria_ptr)
/*  Description   : Get the current carrier handler cross slot criteria
 *                  machine constant
 *  Input(s)      : -
 *  Output(s)     : cross_slot_criteria_ptr: Pointer to machines constants
 *  InOut(s)      : -
 *  Returns       : OK
 *                  WHXT_SYSTEM_ERROR
 *  Preconditions : -
 *  Postconditions: -
 *  Notes         : HWCI-calibration
 */
{
   int                         result     = OK;
   char                        *func_name = "WHAV_rq_get_mc_ch_cross_slot_criteria";
   WHAVXAxMC_ch_constants_struct *ch_mc_ptr = NULL;

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (cross_slot_criteria_ptr=%s)",
              WHAVSPSU_pointer_to_string(cross_slot_criteria_ptr));

   if (OK == result)
   {
      result = WHAVSPMC_get_ch_constants(&ch_mc_ptr);
   }

   if (OK == result)
   {
      *cross_slot_criteria_ptr = ch_mc_ptr->cross_slot_criteria;
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT_DOUBLE("cross_slot_criteria_ptr", cross_slot_criteria_ptr,
                             result);

   return result;
} /* End of WHAV_rq_get_mc_ch_cross_slot_criteria() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_default_mc_ch_cross_slot_criteria(
     /*@unused@*/ WHAV_object_t *object_p,
                  double      *cross_slot_criteria_ptr)
/*  Description   : Get the default carrier handler cross slot criteria
 *                  machine constant
 *  Input(s)      : -
 *  Output(s)     : cross_slot_criteria_ptr: Pointer to machines constants
 *  InOut(s)      : -
 *  Returns       : OK
 *                  WHXT_SYSTEM_ERROR
 *  Preconditions : -
 *  Postconditions: -
 *  Notes         : -
 */
{
   int  result     = OK;
   char *func_name = "WHAV_rq_get_default_mc_ch_cross_slot_criteria";

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (cross_slot_criteria_ptr=%s)",
              WHAVSPSU_pointer_to_string(cross_slot_criteria_ptr));

   if (OK == result)
   {
      result = WHAVAPGE_get_default_mc_ch_cross_slot_criteria(cross_slot_criteria_ptr);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT_DOUBLE("cross_slot_criteria_ptr", cross_slot_criteria_ptr,
                             result);

   return result;
} /* End of WHAV_rq_get_default_mc_ch_cross_slot_criteria() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_minimum_mc_ch_cross_slot_criteria(
     /*@unused@*/ WHAV_object_t *object_p,
                  double      *cross_slot_criteria_ptr)
/*  Description   : Get the minimum carrier handler cross slot criteria
 *                  machine constant
 *  Input(s)      : -
 *  Output(s)     : cross_slot_criteria_ptr: Pointer to machines constants
 *  InOut(s)      : -
 *  Returns       : OK
 *                  WHXT_SYSTEM_ERROR
 *  Preconditions : -
 *  Postconditions: -
 *  Notes         : -
 */
{
   int  result     = OK;
   char *func_name = "WHAV_rq_get_minimum_mc_ch_cross_slot_criteria";

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (cross_slot_criteria_ptr=%s)",
              WHAVSPSU_pointer_to_string(cross_slot_criteria_ptr));

   if (OK == result)
   {
      result = WHAVAPGE_get_minimum_mc_ch_cross_slot_criteria(cross_slot_criteria_ptr);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT_DOUBLE("cross_slot_criteria_ptr", cross_slot_criteria_ptr,
                             result);

   return result;
} /* End of WHAV_rq_get_minimum_mc_ch_cross_slot_criteria() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_maximum_mc_ch_cross_slot_criteria(
     /*@unused@*/ WHAV_object_t *object_p,
                  double      *cross_slot_criteria_ptr)
/*  Description   : Get the maximum carrier handler cross slot criteria
 *                  machine constant
 *  Input(s)      : -
 *  Output(s)     : cross_slot_criteria_ptr: Pointer to machines constants
 *  InOut(s)      : -
 *  Returns       : OK
 *                  WHXT_SYSTEM_ERROR
 *  Preconditions : -
 *  Postconditions: -
 *  Notes         : -
 */
{
   int  result     = OK;
   char *func_name = "WHAV_rq_get_maximum_mc_ch_cross_slot_criteria";

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (cross_slot_criteria_ptr=%s)",
              WHAVSPSU_pointer_to_string(cross_slot_criteria_ptr));

   if (OK == result)
   {
      result = WHAVAPGE_get_maximum_mc_ch_cross_slot_criteria(cross_slot_criteria_ptr);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT_DOUBLE("cross_slot_criteria_ptr", cross_slot_criteria_ptr,
                             result);

   return result;
} /* End of WHAV_rq_get_maximum_mc_ch_cross_slot_criteria() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_mc_ch_wafer_pitch(/*@unused@*/ WHAV_object_t *object_p,
                                             double      *wafer_pitch_ptr)
/*  Description   : Get the current carrier handler wafer pitch
 *                  machine constant
 *  Input(s)      : -
 *  Output(s)     : wafer_pitch_ptr: Pointer to machines constants
 *  InOut(s)      : -
 *  Returns       : OK
 *                  WHXT_SYSTEM_ERROR
 *  Preconditions : -
 *  Postconditions: -
 *  Notes         : HWCI-calibration
 */
{
   int  result     = OK;
   char *func_name = "WHAV_rq_get_mc_ch_wafer_pitch";

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (wafer_pitch_ptr=%s)",
              WHAVSPSU_pointer_to_string(wafer_pitch_ptr));

   if (OK == result)
   {
      result = WHAVPECH_get_mc_ch_wafer_pitch(wafer_pitch_ptr);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT_DOUBLE("wafer_pitch_ptr", wafer_pitch_ptr,
                             result);

   return result;
} /* End of WHAV_rq_get_mc_ch_wafer_pitch() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_mc_ch_calib_edges_offset(
                  /*@unused@*/ WHAV_object_t *object_p,
                               double      *calib_edges_offset_ptr)
/*  Description   : Get the current carrier handler calib edges offset
 *                  machine constant
 *  Input(s)      : -
 *  Output(s)     : calib_edges_offset_ptr: Pointer to machines constants
 *  InOut(s)      : -
 *  Returns       : OK
 *                  WHXT_SYSTEM_ERROR
 *  Preconditions : -
 *  Postconditions: -
 *  Notes         : HWCI-calibration
 */
{
   int  result          = OK;
   char *func_name      = "WHAV_rq_get_mc_ch_calib_edges_offset";
   WHAVXAxMC_ch_constants_struct  *ch_mc_ptr      = NULL;

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (calib_edges_offset_ptr=%s)",
              WHAVSPSU_pointer_to_string(calib_edges_offset_ptr));

   if (OK == result)
   {
      result = WHAVSPMC_get_ch_constants(&ch_mc_ptr);
   }

   if (OK == result)
   {
      *calib_edges_offset_ptr = ch_mc_ptr->calib_edges_offset;
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT_DOUBLE("calib_edges_offset_ptr", calib_edges_offset_ptr,
                             result);

   return result;
} /* End of WHAV_rq_get_mc_ch_calib_edges_offset() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_get_mc_ch_sensor_valid(
                  /*@unused@*/ WHAV_object_t *object_p,
                               bool        *sensor_valid_ptr)
/*  Description   : Get the current carrier handler sensor valid
 *                  machine constant
 *  Input(s)      : -
 *  Output(s)     : sensor_valid_ptr Pointer to machines constants
 *  InOut(s)      : -
 *  Returns       : OK
 *                  WHXT_SYSTEM_ERROR
 *  Preconditions : -
 *  Postconditions: -
 *  Notes         : HWCI-calibration
 */
{
   int                         result     = OK;
   char                        *func_name = "WHAV_rq_get_mc_ch_sensor_valid";
   WHAVXAxMC_ch_constants_struct *ch_mc_ptr = NULL;

   THXAtrace(CC, THXA_TRACE_INT, func_name,
              "> (sensor_valid_ptr=%s)",
              WHAVSPSU_pointer_to_string(sensor_valid_ptr));

   if (OK == result)
   {
      result = WHAVSPMC_get_ch_constants(&ch_mc_ptr);
   }

   if (OK == result)
   {
      *sensor_valid_ptr = ch_mc_ptr->sensor_valid;
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT_BOOL("sensor_valid_ptr", sensor_valid_ptr,
                           result);

   return result;
} /* End of WHAV_rq_get_mc_ch_sensor_valid() */


/*-------------------------------------------------------------------------*/
int WHAV_rq_set_mc_ch_sensor_valid(
                        /*@unused@*/ WHAV_object_t *object_p,
                                     bool         sensor_valid,
                                     char         purpose_str[])
/*  Description   : Update the current carrier handler sensor valid
 *                  machine constant
 *  Input(s)      : sensor_valid Sensor valid
 *                  purpose_str  Reason for update
 *  Output(s)     : -
 *  InOut(s)      : -
 *  Returns       : OK
 *                  WHXT_SYSTEM_ERROR
 *  Preconditions : -
 *  Postconditions: -
 *  Notes         : -
 */
{
   int  result          = OK;
   char *func_name      = "WHAV_rq_set_mc_ch_sensor_valid";
   WHAVXAxMC_ch_constants_struct  *ch_mc_ptr      = NULL;

   THXAtrace(CC, THXA_TRACE_INT, func_name, "> ("
             "sensor_valid=%b, "
             "purpose_str=%s"
             ")",
             sensor_valid,
             purpose_str);

   if (OK == result)
   {
      result = WHAVSPMC_get_ch_constants(&ch_mc_ptr);
   }

   if (OK == result)
   {
      ch_mc_ptr->sensor_valid = sensor_valid;
      result = WHAVSPMC_set_ch_constants(ch_mc_ptr, purpose_str);
   }

   WHAVSPER_LC(result, WHXT_SYSTEM_ERROR);

   WHAVSPTH_TRACE_OUT(result);

   return result;
} /* End of WHAV_rq_set_mc_ch_sensor_valid() */



